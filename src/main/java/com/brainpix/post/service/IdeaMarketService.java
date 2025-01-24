package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.IdeaMarketErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.joining.service.PriceService;
import com.brainpix.post.converter.CreateIdeaMarketConverter;
import com.brainpix.post.converter.GetIdeaCommentListDtoConverter;
import com.brainpix.post.converter.GetIdeaDetailDtoConverter;
import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.converter.GetPopularIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaCommentListDto;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.entity.Comment;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.CommentRepository;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdeaMarketService {

	private final SavedPostRepository savedPostRepository;
	private final IdeaMarketRepository ideaMarketRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PriceService priceService;
	private final CreateIdeaMarketConverter createIdeaMarketConverter;

	@Transactional
	public Long createIdeaMarket(Long userId, IdeaMarketCreateDto createDto) {

		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(IdeaMarketErrorCode.USER_NOT_FOUND));

		Price price = priceService.createIdeaMarketPrice(createDto.getPriceDto());

		IdeaMarket ideaMarket = createIdeaMarketConverter.convertToIdeaMarket(createDto, writer, price);

		ideaMarketRepository.save(ideaMarket);

		return ideaMarket.getId();
	}

	@Transactional
	public void updateIdeaMarket(Long ideaId, Long userId, IdeaMarketUpdateDto updateDto) {
		IdeaMarket ideaMarket = ideaMarketRepository.findById(ideaId)
			.orElseThrow(() -> new BrainPixException(IdeaMarketErrorCode.IDEA_NOT_FOUND));

		ideaMarket.validateWriter(userId);

		// CollaborationHub 고유 필드 업데이트
		ideaMarket.updateIdeaMarketFields(updateDto);

		ideaMarketRepository.save(ideaMarket);
	}

	@Transactional
	public void deleteIdeaMarket(Long ideaId, Long userId) {
		IdeaMarket ideaMarket = ideaMarketRepository.findById(ideaId)
			.orElseThrow(() -> new BrainPixException(IdeaMarketErrorCode.IDEA_NOT_FOUND));

		ideaMarket.validateWriter(userId);

		try {
			ideaMarketRepository.delete(ideaMarket);
		} catch (Exception e) {
			throw new BrainPixException(IdeaMarketErrorCode.IDEA_DELETE_FAILED);
		}
	}

	// 아이디어 메인페이지에서 검색 조건을 적용하여 아이디어 목록을 반환합니다.
	@Transactional(readOnly = true)
	public GetIdeaListDto.Response getIdeaList(GetIdeaListDto.Parameter parameter) {

		// 아이디어-저장수 쌍으로 반환된 결과
		Page<Object[]> result = ideaMarketRepository.findIdeaListWithSaveCount(parameter.getType(),
			parameter.getKeyword(), parameter.getCategory(), parameter.getOnlyCompany(), parameter.getSortType(),
			parameter.getPageable());

		return GetIdeaListDtoConverter.toResponse(result);
	}

	// 아이디어 식별자 값을 입력받아 상세보기에 관한 내용을 반환합니다.
	@Transactional(readOnly = true)
	public GetIdeaDetailDto.Response getIdeaDetail(GetIdeaDetailDto.Parameter parameter) {

		// 아이디어 조회
		IdeaMarket ideaMarket = ideaMarketRepository.findById(parameter.getIdeaId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 조회
		User writer = ideaMarket.getWriter();

		// 아이디어의 저장 횟수
		Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());

		// 작성자의 아이디어 개수
		Long totalIdeas = ideaMarketRepository.countByWriterId(writer.getId());

		// 작성자의 협업 횟수
		Long totalCollaborations = collectionGatheringRepository.countByJoinerIdAndAccepted(writer.getId(), true);

		return GetIdeaDetailDtoConverter.toResponse(ideaMarket, writer, saveCount, totalIdeas, totalCollaborations);
	}

	// 아이디어 식별자 값을 입력받아 댓글 목록을 조회합니다.
	@Transactional(readOnly = true)
	public GetIdeaCommentListDto.Response getIdeaCommentList(GetIdeaCommentListDto.Parameter parameter) {

		// 아이디어 조회
		IdeaMarket ideaMarket = ideaMarketRepository.findById(parameter.getIdeaId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 게시글에 연관된 모든 댓글을 조회
		Page<Comment> comments = commentRepository.findByParentPostId(ideaMarket.getId(), parameter.getPageable());

		return GetIdeaCommentListDtoConverter.toResponse(comments);
	}

	// 저장순으로 아이디어를 조회합니다.
	@Transactional(readOnly = true)
	public GetPopularIdeaListDto.Response getPopularIdeaList(GetPopularIdeaListDto.Parameter parameter) {

		// 아이디어-저장수 쌍으로 반환된 결과
		Page<Object[]> ideaMarkets = ideaMarketRepository.findPopularIdeaListWithSaveCount(parameter.getType(),
			parameter.getPageable());

		return GetPopularIdeaListDtoConverter.toResponse(ideaMarkets);
	}
}
