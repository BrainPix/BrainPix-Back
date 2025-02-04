package com.brainpix.post.dto.mypostdto;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.profile.entity.Specialization;

public record MyCollaborationHubDetailResponse(
	Long postId,
	Specialization specialization,
	String title,
	LocalDateTime deadLine,
	String thumbnailImage,
	List<CollaborationApplicationStatusResponse> applicationStatus,
	List<CollaborationCurrentMemberResponse> currentMembers
) {

	public static MyCollaborationHubDetailResponse from(
		CollaborationHub collaborationHub,
		List<CollaborationApplicationStatusResponse> applicationStatus,
		List<CollaborationCurrentMemberResponse> currentMembers
	) {
		return new MyCollaborationHubDetailResponse(
			collaborationHub.getId(),
			collaborationHub.getSpecialization(),
			collaborationHub.getTitle(),
			collaborationHub.getDeadline(),
			collaborationHub.getFirstImage(),
			applicationStatus,
			currentMembers
		);
	}
}
