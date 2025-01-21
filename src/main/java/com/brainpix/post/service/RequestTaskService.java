package com.brainpix.post.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.brainpix.joining.dto.RequestTaskApplicantsResponse;
import com.brainpix.joining.dto.RequestTaskBuyerResponse;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.post.dto.requesttask.RequestTaskDetailResponse;
import com.brainpix.post.dto.requesttask.RequestTaskPreviewResponse;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.post.repository.requesttask.RequestTaskRecruitmentRepository;
import com.brainpix.post.repository.requesttask.RequestTaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestTaskService {

	private final RequestTaskRepository taskRepository;
	private final RequestTaskRecruitmentRepository recruitmentRepository;
	private final SavedPostRepository savedPostRepository;

	/**
	 * 요청 과제 게시물 미리보기
	 */
	public List<RequestTaskPreviewResponse> getRequestTaskPreviews(Long userId) {
		List<RequestTask> tasks = taskRepository.findAllByWriterIdFetchJoin(userId);

		return tasks.stream()
			.map(task -> {
				Long saveCount = savedPostRepository.countByPostId(task.getId());
				return RequestTaskPreviewResponse.from(task, saveCount);
			})
			.toList();
	}

	/**
	 * 요청 과제 상세보기
	 */
	public RequestTaskDetailResponse getRequestTaskDetail(Long taskId) {
		RequestTask requestTask = findRequestTaskById(taskId);
		List<RequestTaskRecruitment> recruitments = findRecruitmentsByTaskId(taskId);

		List<RequestTaskApplicantsResponse> applicants = mapRecruitmentApplicants(recruitments);
		return RequestTaskDetailResponse.from(requestTask, applicants);
	}

	private RequestTask findRequestTaskById(Long taskId) {
		return taskRepository.findById(taskId)
			.orElseThrow(() -> new IllegalArgumentException("RequestTask not found"));
	}

	private List<RequestTaskRecruitment> findRecruitmentsByTaskId(Long taskId) {
		return recruitmentRepository.findAllWithPurchasesByTaskId(taskId);
	}

	private List<RequestTaskApplicantsResponse> mapRecruitmentApplicants(List<RequestTaskRecruitment> recruitments) {
		return recruitments.stream()
			.map(recruitment -> {
				Map<Boolean, List<RequestTaskBuyerResponse>> buyersByStatus = recruitment.getPurchases().stream()
					.collect(Collectors.partitioningBy(
						RequestTaskPurchasing::getAccepted,
						Collectors.mapping(RequestTaskBuyerResponse::from, Collectors.toList())
					));

				return RequestTaskApplicantsResponse.builder()
					.recruitmentId(recruitment.getId())
					.domain(recruitment.getDomain())
					.acceptedBuyers(buyersByStatus.get(true))
					.pendingBuyers(buyersByStatus.get(false))
					.build();
			})
			.toList();
	}
}


