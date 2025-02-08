package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.IdeaMarketErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import com.brainpix.joining.service.PriceService;
import com.brainpix.post.converter.CreateIdeaMarketConverter;
import com.brainpix.post.converter.GetIdeaDetailDtoConverter;
import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.converter.GetIdeaPurchasePageDtoConverter;
import com.brainpix.post.converter.GetPopularIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetIdeaPurchasePageDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.PostRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.security.authority.BrainpixAuthority;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdeaMarketService {

	private final SavedPostRepository savedPostRepository;
	private final IdeaMarketRepository ideaMarketRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;
	private final UserRepository userRepository;
	private final PriceService priceService;
	private final CreateIdeaMarketConverter createIdeaMarketConverter;
	private final RequestTaskPurchasingRepository requestTaskPurchasingRepository;
	private final PostRepository postRepository;

	@Transactional
	public Long createIdeaMarket(Long userId, IdeaMarketCreateDto createDto) {

		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.USER_NOT_FOUND));

		Price price = priceService.createIdeaMarketPrice(createDto.getPriceDto());

		IdeaMarket ideaMarket = createIdeaMarketConverter.convertToIdeaMarket(createDto, writer, price);

		ideaMarketRepository.save(ideaMarket);

		return ideaMarket.getId();
	}

	@Transactional
	public void updateIdeaMarket(Long ideaId, Long userId, IdeaMarketUpdateDto updateDto) {
		IdeaMarket ideaMarket = ideaMarketRepository.findById(ideaId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		ideaMarket.validateWriter(userId);

		// CollaborationHub 고유 필드 업데이트
		ideaMarket.updateIdeaMarketFields(updateDto);

		ideaMarketRepository.save(ideaMarket);
	}

	@Transactional
	public void deleteIdeaMarket(Long ideaId, Long userId) {
		IdeaMarket ideaMarket = ideaMarketRepository.findById(ideaId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		ideaMarket.validateWriter(userId);

		ideaMarketRepository.delete(ideaMarket);
	}

	// 아이디어 메인페이지에서 검색 조건을 적용하여 아이디어 목록을 반환합니다.
	@Transactional(readOnly = true)
	public CommonPageResponse<GetIdeaListDto.IdeaDetail> getIdeaList(GetIdeaListDto.Parameter parameter) {

		// 아이디어-저장수 쌍으로 반환된 결과
		Page<Object[]> result = ideaMarketRepository.findIdeaListWithSaveCount(parameter.getUserId(),
			parameter.getType(),
			parameter.getKeyword(), parameter.getCategory(), parameter.getOnlyCompany(), parameter.getSortType(),
			parameter.getPageable());

		return GetIdeaListDtoConverter.toResponse(result);
	}

	// 아이디어 식별자 값을 입력받아 상세보기에 관한 내용을 반환합니다.
	@Transactional
	public GetIdeaDetailDto.Response getIdeaDetail(GetIdeaDetailDto.Parameter parameter) {

		// 유저 조회
		User user = userRepository.findById(parameter.getUserId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		// 아이디어 조회
		IdeaMarket ideaMarket = ideaMarketRepository.findById(parameter.getIdeaId())
			.orElseThrow(() -> new BrainPixException(IdeaMarketErrorCode.IDEA_NOT_FOUND));

		// 개인이 기업 게시물을 상세보기 하는 경우 처리
		if (ideaMarket.getPostAuth().equals(PostAuth.COMPANY) && user.getAuthority()
			.equals(BrainpixAuthority.INDIVIDUAL)) {
			throw new BrainPixException(IdeaMarketErrorCode.FORBIDDEN_ACCESS);
		}

		// 조회수 증가
		postRepository.increaseViewCount(ideaMarket.getId());

		// 작성자 조회
		User writer = ideaMarket.getWriter();

		// 아이디어의 저장 횟수
		Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());

		// 작성자의 아이디어 개수
		Long totalIdeas = ideaMarketRepository.countByWriterId(writer.getId());

		// 작성자의 협업 횟수 (협업 승인수 + 협업 초기 멤버 + 요청 과제 승인수)
		Long totalCollaborations = collectionGatheringRepository.countByJoinerIdAndAccepted(writer.getId(), true)
			+ collectionGatheringRepository.countByJoinerIdAndInitialGathering(
			writer.getId(), true) + requestTaskPurchasingRepository.countByBuyerIdAndAccepted(writer.getId(), true);

		// 저장한 게시글인지 확인
		Boolean isSavedPost = savedPostRepository.existsByUserAndPost(user, ideaMarket);

		// 내 게시글인지 확인
		Boolean isMyPost = writer.equals(user);

		return GetIdeaDetailDtoConverter.toResponse(ideaMarket, writer, saveCount, totalIdeas, totalCollaborations,
			isSavedPost, isMyPost);
	}

	// 저장순으로 아이디어를 조회합니다.
	@Transactional(readOnly = true)
	public CommonPageResponse<GetPopularIdeaListDto.IdeaDetail> getPopularIdeaList(
		GetPopularIdeaListDto.Parameter parameter) {

		// 아이디어-저장수 쌍으로 반환된 결과
		Page<Object[]> ideaMarkets = ideaMarketRepository.findPopularIdeaListWithSaveCount(parameter.getUserId(),
			parameter.getType(),
			parameter.getPageable());

		return GetPopularIdeaListDtoConverter.toResponse(ideaMarkets);
	}

	@Transactional(readOnly = true)
	public GetIdeaPurchasePageDto.Response getIdeaPurchasePage(GetIdeaPurchasePageDto.Parameter parameter) {

		// 유저 조회
		User user = userRepository.findById(parameter.getUserId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		// 아이디어 조회
		IdeaMarket ideaMarket = ideaMarketRepository.findById(parameter.getIdeaId())
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		// 판매자 정보 조회
		User seller = ideaMarket.getWriter();

		// 개인이 기업 게시물을 구매하려는 경우 처리
		if (ideaMarket.getPostAuth().equals(PostAuth.COMPANY) && user.getAuthority()
			.equals(BrainpixAuthority.INDIVIDUAL)) {
			throw new BrainPixException(IdeaMarketErrorCode.FORBIDDEN_ACCESS);
		}
		// 글 작성자가 구매하려는 경우 처리
		if (seller == user) {
			throw new BrainPixException(IdeaMarketErrorCode.FORBIDDEN_ACCESS);
		}

		return GetIdeaPurchasePageDtoConverter.toResponse(ideaMarket, seller);
	}
}
