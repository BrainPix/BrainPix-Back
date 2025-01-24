package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.converter.GetIdeaCommentListDtoConverter;
import com.brainpix.post.converter.GetIdeaDetailDtoConverter;
import com.brainpix.post.converter.GetIdeaListDtoConverter;
import com.brainpix.post.converter.GetPopularIdeaListDtoConverter;
import com.brainpix.post.dto.GetIdeaCommentListDto;
import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.dto.GetPopularIdeaListDto;
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

	public Long createIdeaMarket(IdeaMarketCreateDto createDto) {
		User writer = userRepository.findById(1L)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		Price price = Price.builder()
			//.totalQuantity(createDto.getTotalQuantity())
			//.occupiedQuantity(null)
			.price(createDto.getPrice())
			.build();

		IdeaMarket ideaMarket = IdeaMarket.builder()
			.writer(writer)
			.title(createDto.getTitle())
			.content(createDto.getContent())
			.category(createDto.getCategory())
			.specialization(createDto.getSpecialization())
			.ideaMarketType(createDto.getIdeaMarketType())
			.openMyProfile(createDto.getOpenMyProfile())
			.viewCount(0L) // 초기 조회수는 0으로 설정
			.imageList(createDto.getImageList())
			.attachmentFileList(createDto.getAttachmentFileList())
			.postAuth(createDto.getPostAuth())
			.price(price)
			.build();

		IdeaMarket savedIdeaMarket = ideaMarketRepository.save(ideaMarket);
		return savedIdeaMarket.getId();
	}

	public void updateIdeaMarket(Long id, IdeaMarketUpdateDto updateDto) {
		IdeaMarket existingTask = ideaMarketRepository.findById(id)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		// CollaborationHub 고유 필드 업데이트
		existingTask.updateIdeaMarketFields(updateDto);

		ideaMarketRepository.save(existingTask);
	}


	public void deleteIdeaMarket(Long id) {
		if (!ideaMarketRepository.existsById(id)) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
		ideaMarketRepository.deleteById(id);
	}

	// 아이디어 메인페이지에서 검색 조건을 적용하여 아이디어 목록을 반환합니다.
	@Transactional(readOnly = true)
	public GetIdeaListDto.Response getIdeaList(GetIdeaListDto.Parameter parameter) {

		// 아이디어-저장수 쌍으로 반환된 결과
		Page<Object[]> result = ideaMarketRepository.findIdeaListWithSaveCount(parameter.getType(),
			parameter.getKeyword(), parameter.getCategory(), parameter.getOnlyCompany(), parameter.getSortType(), parameter.getPageable());

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
		Page<Object[]> ideaMarkets = ideaMarketRepository.findPopularIdeaListWithSaveCount(parameter.getType(), parameter.getPageable());

		return GetPopularIdeaListDtoConverter.toResponse(ideaMarkets);
	}
}
