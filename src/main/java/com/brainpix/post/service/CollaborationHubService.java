package com.brainpix.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.RequestTaskErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.converter.CreateCollaborationHubConverter;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.converter.GetCollaborationHubDetailDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubListDtoConverter;
import com.brainpix.post.dto.GetCollaborationHubDetailDto;
import com.brainpix.post.dto.GetCollaborationHubListDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubService {

	private final CollaborationHubRepository collaborationHubRepository;
	private final CollaborationHubRecruitmentService collaborationHubRecruitmentService;
	private final UserRepository userRepository;
	private final CreateCollaborationHubConverter createCollaborationHubConverter;
	private final CollaborationHubInitialMemberService collaborationHubInitialMemberService;
	private final SavedPostRepository savedPostRepository;
	private final IdeaMarketRepository ideaMarketRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;

	@Transactional
	public Long createCollaborationHub(Long userId, CollaborationHubCreateDto createDto) {

		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.USER_NOT_FOUND));

		CollaborationHub collaborationHub = createCollaborationHubConverter.convertToCollaborationHub(createDto,
			writer);

		collaborationHubRepository.save(collaborationHub);
		collaborationHubRecruitmentService.createRecruitments(collaborationHub, createDto.getRecruitments());
		collaborationHubInitialMemberService.createInitialMembers(collaborationHub, createDto.getInitialMembers());

		return collaborationHub.getId();
	}

	@Transactional
	public void updateCollaborationHub(Long collaborationId, Long userId, CollaborationHubUpdateDto updateDto) {
		CollaborationHub collaboration = collaborationHubRepository.findById(collaborationId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 검증 로직 추가
		collaboration.validateWriter(userId);

		// CollaborationHub 고유 필드 업데이트
		collaboration.updateCollaborationHubFields(updateDto);

		collaborationHubRepository.save(collaboration);
	}

	@Transactional
	public void deleteCollaborationHub(Long collaborationId, Long userId) {
		CollaborationHub collaboration = collaborationHubRepository.findById(collaborationId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 검증 로직 추가
		collaboration.validateWriter(userId);

		collaborationHubRepository.deleteById(collaborationId);
	}

	@Transactional(readOnly = true)
	public GetCollaborationHubListDto.Response getCollaborationHubList(GetCollaborationHubListDto.Parameter parameter) {

		// 협업 게시글-저장수 쌍으로 반환된 결과
		Page<Object[]> result = collaborationHubRepository.findCollaborationListWithSaveCount(
			parameter.getKeyword(), parameter.getCategory(), parameter.getOnlyCompany(), parameter.getSortType(),
			parameter.getPageable());

		return GetCollaborationHubListDtoConverter.toResponse(result);
	}

	@Transactional(readOnly = true)
	public GetCollaborationHubDetailDto.Response getCollaborationHubDetail(
		GetCollaborationHubDetailDto.Parameter parameter) {

		// 협업 게시글 조회
		CollaborationHub collaborationHub = collaborationHubRepository.findById(parameter.getCollaborationId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 조회
		User writer = collaborationHub.getWriter();
		System.out.println(writer.getId());

		// 아이디어의 저장 횟수
		Long saveCount = savedPostRepository.countByPostId(collaborationHub.getId());

		// 작성자의 아이디어 개수
		Long totalIdeas = ideaMarketRepository.countByWriterId(writer.getId());

		// 작성자의 협업 횟수
		Long totalCollaborations = collectionGatheringRepository.countByJoinerIdAndAccepted(writer.getId(), true);

		// 개최 인원
		List<CollectionGathering> collectionGatherings = collectionGatheringRepository.findByCollaborationHubId(
			collaborationHub.getId());

		return GetCollaborationHubDetailDtoConverter.toResponse(collaborationHub, collectionGatherings, writer,
			saveCount, totalIdeas, totalCollaborations);
	}
}
