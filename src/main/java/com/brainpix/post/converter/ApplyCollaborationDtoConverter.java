package com.brainpix.post.converter;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.post.dto.ApplyCollaborationDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.user.entity.User;

public class ApplyCollaborationDtoConverter {

	public static ApplyCollaborationDto.Parameter toParameter(Long collaborationId, Long userId,
		ApplyCollaborationDto.Request request) {

		return ApplyCollaborationDto.Parameter.builder()
			.collaborationId(collaborationId)
			.userId(userId)
			.collaborationRecruitmentId(request.getCollaborationRecruitmentId())
			.isOpenProfile(request.getIsOpenProfile())
			.message(request.getMessage())
			.build();
	}

	public static CollectionGathering toCollectionGathering(User user, CollaborationRecruitment recruitment,
		Boolean openProfile, String message) {

		return CollectionGathering.builder()
			.joiner(user)
			.accepted(null)    // 보류 상태
			.initialGathering(false)
			.openProfile(openProfile)
			.message(message)
			.collaborationRecruitment(recruitment)
			.build();
	}

	public static ApplyCollaborationDto.Response toResponse(CollectionGathering collectionGathering) {

		return ApplyCollaborationDto.Response.builder()
			.CollectionGatheringId(collectionGathering.getId())
			.build();
	}
}
