package com.brainpix.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.converter.ApplyCollaborationDtoConverter;
import com.brainpix.post.dto.ApplyCollaborationDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.post.repository.CollaborationRecruitmentRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubService {

	private final CollaborationHubRepository collaborationHubRepository;
	private final CollaborationRecruitmentRepository collaborationRecruitmentRepository;
	private final CollectionGatheringRepository collectionGatheringRepository;
	private final UserRepository userRepository;

	@Transactional
	public void applyCollaboration(ApplyCollaborationDto.Parameter parameter) {

		// 협업 게시글 조회
		CollaborationHub collaboration = collaborationHubRepository.findById(parameter.getCollaborationId())
			.orElseThrow(() -> new BrainPixException(
				CommonErrorCode.RESOURCE_NOT_FOUND));

		// 유저 조회
		User user = userRepository.findById(parameter.getUserId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 지원 분야 조회
		CollaborationRecruitment recruitment = collaborationRecruitmentRepository.findById(
				parameter.getCollaborationRecruitmentId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 지원자가 모두 채워진 경우 예외
		if (recruitment.getGathering().getOccupiedQuantity() >= recruitment.getGathering().getTotalQuantity()) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		// 글 작성자가 신청하는 예외는 필터링
		if (collaboration.getWriter() == user) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		// 이미 지원한 분야인 경우 예외
		if (collectionGatheringRepository.existsByJoinerIdAndCollaborationRecruitmentId(user.getId(),
			recruitment.getId())) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		// 협업 게시글에 속하는 지원 분야인지 확인
		if (recruitment.getParentCollaborationHub() != collaboration) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		// 엔티티 생성
		CollectionGathering collectionGathering = ApplyCollaborationDtoConverter.toCollectionGathering(user,
			recruitment, parameter.getIsOpenProfile(),
			parameter.getMessage());

		// 지원 신청
		collectionGatheringRepository.save(collectionGathering);
	}
}
