package com.brainpix.post.service.mypost;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.kafka.service.AlarmEventService;
import com.brainpix.post.dto.PostCollaborationResponse;
import com.brainpix.post.dto.mypostdto.CollaborationApplicationStatusResponse;
import com.brainpix.post.dto.mypostdto.CollaborationCurrentMemberResponse;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubDetailResponse;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCollaborationHubService {

	private final UserRepository userRepository;
	private final CollaborationHubRepository collaborationHubRepository;
	private final SavedPostRepository savedPostRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;
	private final AlarmEventService alarmEventService;

	public Page<PostCollaborationResponse> findCollaborationPosts(long userId, Pageable pageable) {
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));
		return collaborationHubRepository.findByWriter(writer, pageable)
			.map(collaborationHub -> {
				Long saveCount = savedPostRepository.countByPostId(collaborationHub.getId());
				boolean isSavedPost = savedPostRepository.existsByUserIdAndPostId(userId, collaborationHub.getId());
				long totalQuantity = collaborationHub.getTotalQuantity();
				long occupiedQuantity = collaborationHub.getOccupiedQuantity();
				return PostCollaborationResponse.from(collaborationHub, saveCount, totalQuantity, occupiedQuantity,
					isSavedPost);
			});
	}

	@Transactional
	public MyCollaborationHubDetailResponse getCollaborationHubDetail(Long userId, Long postId) {
		CollaborationHub collaborationHub = collaborationHubRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		collaborationHub.validateWriter(userId);

		List<CollaborationApplicationStatusResponse> applicationStatus = collectionGatheringRepository
			.findByCollaborationRecruitmentInAndAcceptedIsNull(collaborationHub.getCollaborations())
			.stream()
			.map(CollaborationApplicationStatusResponse::from)
			.collect(Collectors.toList());

		List<CollaborationCurrentMemberResponse> currentMembers = collectionGatheringRepository
			.findByCollaborationRecruitmentIn(collaborationHub.getCollaborations())
			.stream()
			.filter(collection -> Boolean.TRUE.equals(collection.getAccepted()) || Boolean.TRUE.equals(
				collection.getInitialGathering()))
			.collect(Collectors.groupingBy(
				collection -> collection.getCollaborationRecruitment().getDomain(),
				Collectors.mapping(collection -> {
					User joiner = collection.getJoiner();
					String userType = (joiner instanceof Individual) ? "개인" : "회사";
					return CollaborationCurrentMemberResponse.AcceptedInfo.from(joiner.getIdentifier(), userType,
						joiner.getId());
				}, Collectors.toList())
			))
			.entrySet()
			.stream()
			.map(entry -> CollaborationCurrentMemberResponse.from(entry.getKey(), entry.getValue()))
			.collect(Collectors.toList());

		return MyCollaborationHubDetailResponse.from(collaborationHub, applicationStatus, currentMembers);

	}

	/**
	 * 지원 수락
	 */
	@Transactional
	public void acceptApplication(Long userId, Long gatheringId) {
		CollectionGathering gathering = collectionGatheringRepository.findById(gatheringId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		CollaborationHub collaborationHub = gathering.getCollaborationRecruitment().getParentCollaborationHub();
		if (!collaborationHub.getWriter().getId().equals(userId)) {
			throw new BrainPixException(CommonErrorCode.METHOD_NOT_ALLOWED);
		}

		if (Boolean.TRUE.equals(gathering.getAccepted())) {
			throw new BrainPixException(CommonErrorCode.METHOD_NOT_ALLOWED);
		}

		gathering.approve();

		alarmEventService.publishCollaborationTaskApprove(
			gathering.getJoiner().getId(),
			gathering.getJoiner().getName(),
			gathering.getCollaborationRecruitment().getParentCollaborationHub().getWriter().getName()
		);
	}

	/**
	 * 지원 거절
	 */
	@Transactional
	public void rejectApplication(Long userId, Long gatheringId) {
		CollectionGathering gathering = collectionGatheringRepository.findById(gatheringId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 지원자가 속한 협업광장의 소유자인지 확인
		CollaborationHub collaborationHub = gathering.getCollaborationRecruitment().getParentCollaborationHub();
		if (!collaborationHub.getWriter().getId().equals(userId)) {
			throw new BrainPixException(CommonErrorCode.METHOD_NOT_ALLOWED);
		}

		gathering.reject();
		collectionGatheringRepository.save(gathering);

		alarmEventService.publishCollaborationTaskReject(
			gathering.getJoiner().getId(),
			gathering.getJoiner().getName(),
			gathering.getCollaborationRecruitment().getParentCollaborationHub().getWriter().getName()
		);
	}

}
