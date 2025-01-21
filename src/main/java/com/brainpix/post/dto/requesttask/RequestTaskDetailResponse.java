package com.brainpix.post.dto.requesttask;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.joining.dto.RequestTaskApplicantsResponse;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.profile.entity.Specialization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTaskDetailResponse {
	private Long id;
	private String title;
	private Specialization specialization;
	private String imageUrl;
	private LocalDateTime deadline;
	private String link;
	private List<RequestTaskApplicantsResponse> applicants;

	public static RequestTaskDetailResponse from(RequestTask requestTask,
		List<RequestTaskApplicantsResponse> applicants) {
		return RequestTaskDetailResponse.builder()
			.id(requestTask.getId())
			.title(requestTask.getTitle())
			.specialization(requestTask.getSpecialization())
			.imageUrl(requestTask.getImageList().isEmpty() ? null : requestTask.getImageList().get(0))
			.deadline(requestTask.getDeadline())
			.link(null) // Add link logic if applicable
			.applicants(applicants)
			.build();
	}
}
