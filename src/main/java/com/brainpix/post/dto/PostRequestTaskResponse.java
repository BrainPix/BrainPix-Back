package com.brainpix.post.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostRequestTaskResponse(
	Long ideaId,
	PostAuth auth,
	String writerImageUrl,
	String writerName,
	String thumbnailImageUrl,
	String title,
	@Schema(type = "string", example = "yyyy-MM-dd HH:mm")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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
