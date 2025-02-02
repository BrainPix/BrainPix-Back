package com.brainpix.post.dto;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.utils.DeadlineUtils;
import com.brainpix.profile.entity.Specialization;

public record SavedPostRequestTaskResponse(
	Long ideaId,
	PostAuth auth,
	String writerImageUrl,
	String writerName,
	String thumbnailImageUrl,
	String title,
	String dDay,
	Specialization specialization,
	Long saveCount,
	Long viewCount
) {
	public static SavedPostRequestTaskResponse from(RequestTask requestTask, Long saveCount) {
		String dDayString = DeadlineUtils.toDDayFormat(requestTask.getDeadline());

		return new SavedPostRequestTaskResponse(
			requestTask.getId(),
			requestTask.getPostAuth(),
			requestTask.getWriter().getProfileImage(),
			requestTask.getWriter().getName(),
			requestTask.getFirstImage(),
			requestTask.getTitle(),
			dDayString,
			requestTask.getSpecialization(),
			saveCount,
			requestTask.getViewCount()
		);
	}
}
