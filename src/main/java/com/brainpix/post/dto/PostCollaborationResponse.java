package com.brainpix.post.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostCollaborationResponse(
	Long ideaId,
	PostAuth auth,
	String writerImageUrl,
	String writerName,
	String thumbnailImageUrl,
	String title,
	@Schema(type = "string", example = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime deadline,
	Specialization specialization,
	Long saveCount,
	Long viewCount,
	Long totalQuantity,
	Long occupiedQuantity
) {
	public static PostCollaborationResponse from(CollaborationHub collaborationHub, Long saveCount,
		Long totalQuantity, Long occupiedQuantity) {
		return new PostCollaborationResponse(
			collaborationHub.getId(),
			collaborationHub.getPostAuth(),
			collaborationHub.getWriter().getProfileImage(),
			collaborationHub.getWriter().getName(),
			collaborationHub.getFirstImage(),
			collaborationHub.getTitle(),
			collaborationHub.getDeadline(),
			collaborationHub.getSpecialization(),
			saveCount,
			collaborationHub.getViewCount(),
			totalQuantity,
			occupiedQuantity
		);
	}
}
