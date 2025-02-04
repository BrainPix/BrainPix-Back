package com.brainpix.post.dto.mypostdto;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.entity.Specialization;

public record MyRequestTaskDetailResponse(
	Long postId,
	Specialization specialization,
	String title,
	LocalDateTime deadLine,
	String thumbnailImage,
	List<ApplicationStatusResponse> applicationStatus,
	List<CurrentMemberResponse> currentMembers

) {
	public static MyRequestTaskDetailResponse from(
		RequestTask requestTask,
		List<ApplicationStatusResponse> applicationStatus,
		List<CurrentMemberResponse> currentMembers
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

