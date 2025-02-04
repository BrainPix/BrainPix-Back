package com.brainpix.post.dto;

import java.time.LocalDateTime;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.entity.Specialization;

public record PostRequestTaskResponse(
	Long ideaId,
	PostAuth auth,
	String writerImageUrl,
	String writerName,
	String thumbnailImageUrl,
	String title,
	LocalDateTime deadline,
	Specialization specialization,
	Long saveCount,
	Long viewCount
) {
	public static PostRequestTaskResponse from(RequestTask requestTask, Long saveCount) {
		return new PostRequestTaskResponse(
			requestTask.getId(),
			requestTask.getPostAuth(),
			requestTask.getWriter().getProfileImage(),
			requestTask.getWriter().getName(),
			requestTask.getFirstImage(),
			requestTask.getTitle(),
			requestTask.getDeadline(),
			requestTask.getSpecialization(),
			saveCount,
			requestTask.getViewCount()
		);
	}
}
