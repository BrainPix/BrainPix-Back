package com.brainpix.post.dto.mypostdto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyCollaborationHubDetailResponse(
	Long postId,
	Specialization specialization,
	String title,
	@Schema(type = "string", example = "yyyy-MM-dd HH:mm")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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
