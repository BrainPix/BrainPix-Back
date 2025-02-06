package com.brainpix.post.dto.mypostdto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

public record MyRequestTaskDetailResponse(
	Long postId,
	Specialization specialization,
	String title,
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime deadLine,
	String thumbnailImage,
	List<RequestTaskApplicationStatusResponse> applicationStatus,
	List<RequestTaskCurrentMemberResponse> currentMembers

) {
	public static MyRequestTaskDetailResponse from(
		RequestTask requestTask,
		List<RequestTaskApplicationStatusResponse> applicationStatus,
		List<RequestTaskCurrentMemberResponse> currentMembers
	) {
		return new MyRequestTaskDetailResponse(
			requestTask.getId(),
			requestTask.getSpecialization(),
			requestTask.getTitle(),
			requestTask.getDeadline(),
			requestTask.getFirstImage(),
			applicationStatus,
			currentMembers
		);
	}
}

