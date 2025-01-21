package com.brainpix.post.dto.requesttask;

import java.time.LocalDateTime;

import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.entity.Specialization;

public record RequestTaskPreviewResponse(
	Long id,
	String writerName,
	String title,
	String imageUrl,
	LocalDateTime deadline,
	Specialization specialization,
	Long saveCount,
	Long viewCount
) {
	public static RequestTaskPreviewResponse from(RequestTask task, long saveCount) {
		return new RequestTaskPreviewResponse(
			task.getId(),
			task.getWriter().getName(),
			task.getTitle(),
			task.getImageList().isEmpty() ? null : task.getImageList().get(0),
			task.getDeadline(),
			task.getSpecialization(),
			saveCount,
			task.getViewCount()
		);
	}
}