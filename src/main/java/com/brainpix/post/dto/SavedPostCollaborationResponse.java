package com.brainpix.post.dto;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.utils.DeadlineUtils;
import com.brainpix.profile.entity.Specialization;

public record SavedPostCollaborationResponse(
	Long ideaId,
	PostAuth auth,
	String writerImageUrl,
	String writerName,
	String thumbnailImageUrl,
	String title,
	String dDay,
	Specialization specialization,
	Long saveCount,
	Long viewCount,
	Long totalQuantity,
	Long occupiedQuantity
) {
	public static SavedPostCollaborationResponse from(CollaborationHub collaborationHub, Long saveCount,
		Long totalQuantity, Long occupiedQuantity) {
		String dDayString = DeadlineUtils.toDDayFormat(collaborationHub.getDeadline());

		return new SavedPostCollaborationResponse(
			collaborationHub.getId(),
			collaborationHub.getPostAuth(),
			collaborationHub.getWriter().getProfileImage(),
			collaborationHub.getWriter().getName(),
			collaborationHub.getFirstImage(),
			collaborationHub.getTitle(),
			dDayString,
			collaborationHub.getSpecialization(),
			saveCount,
			collaborationHub.getViewCount(),
			totalQuantity,
			occupiedQuantity
		);
	}
}
