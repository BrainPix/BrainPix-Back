package com.brainpix.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CollectionErrorCode;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.code.error.RequestTaskErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import com.brainpix.post.converter.ApplyRequestTaskDtoConverter;
import com.brainpix.post.converter.CreateRequestTaskConverter;
import com.brainpix.post.dto.ApplyRequestTaskDto;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.post.repository.RequestTaskRecruitmentRepository;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestTaskCommandService {

	private final RequestTaskRepository requestTaskRepository;
	private final RequestTaskRecruitmentService recruitmentService;
	private final UserRepository userRepository;
	private final CreateRequestTaskConverter createRequestTaskConverter;
	private final RequestTaskPurchasingRepository requestTaskPurchasingRepository;
	private final RequestTaskRecruitmentRepository requestTaskRecruitmentRepository;

	@Transactional
	public Long createRequestTask(Long userId, RequestTaskCreateDto createDto) {

		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.USER_NOT_FOUND));

		RequestTask requestTask = createRequestTaskConverter.convertToRequestTask(createDto, writer);

		requestTaskRepository.save(requestTask);
		recruitmentService.createRecruitments(requestTask, createDto.getRecruitments());

		return requestTask.getId();
	}

	@Transactional
	public void updateRequestTask(Long taskId, Long userId, RequestTaskUpdateDto updateDto) {
		RequestTask requestTask = requestTaskRepository.findById(taskId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		// 작성자 검증 로직 추가
		requestTask.validateWriter(userId);

		// RequestTask 고유 필드 업데이트
		requestTask.updateRequestTaskFields(updateDto);

		requestTaskRepository.save(requestTask);
	}

	@Transactional
	public void deleteRequestTask(Long taskId, Long userId) {
		RequestTask requestTask = requestTaskRepository.findById(taskId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		// 작성자 검증 로직 추가
		requestTask.validateWriter(userId);

		requestTaskRepository.delete(requestTask);
	}

	@Transactional
	public ApplyRequestTaskDto.Response applyRequestTask(ApplyRequestTaskDto.Parameter parameter) {

		// 요청 과제 조회
		RequestTask requestTask = requestTaskRepository.findById(parameter.getTaskId())
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		// 유저 조회
		User user = userRepository.findById(parameter.getUserId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		// 지원 분야 조회
		RequestTaskRecruitment requestTaskRecruitment = requestTaskRecruitmentRepository.findById(
				parameter.getRequestRecruitmentId())
			.orElseThrow(() -> new BrainPixException(CollectionErrorCode.COLLECTION_NOT_FOUND));

		// 지원자가 모두 채워진 경우 예외
		if (requestTaskRecruitment.getPrice().getOccupiedQuantity() >= requestTaskRecruitment.getPrice()
			.getTotalQuantity()) {
			throw new BrainPixException(CollectionErrorCode.RECRUITMENT_ALREADY_FULL);
		}

		// 글 작성자가 신청하는 예외는 필터링
		if (requestTask.getWriter() == user) {
			throw new BrainPixException(PostErrorCode.FORBIDDEN_ACCESS);
		}

		// 이미 지원한 분야인 경우 예외
		if (requestTaskPurchasingRepository.existsByBuyerIdAndRequestTaskRecruitmentId(user.getId(),
			requestTaskRecruitment.getId())) {
			throw new BrainPixException(CollectionErrorCode.RECRUITMENT_ALREADY_APPLY);
		}

		// 요청 과제에 속하는 지원 분야인지 확인
		if (requestTaskRecruitment.getRequestTask() != requestTask) {
			throw new BrainPixException(CollectionErrorCode.RECRUITMENT_NOT_FOUND);
		}

		// 엔티티 생성
		RequestTaskPurchasing requestTaskPurchasing = ApplyRequestTaskDtoConverter.toRequestTaskPurchasing(user,
			requestTaskRecruitment, parameter.getIsOpenProfile(), parameter.getMessage());

		// 지원 신청
		requestTaskPurchasingRepository.save(requestTaskPurchasing);

		return ApplyRequestTaskDtoConverter.toResponse(requestTaskPurchasing);
	}
}
