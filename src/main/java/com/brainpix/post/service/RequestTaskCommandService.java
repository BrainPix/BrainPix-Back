package com.brainpix.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.RequestTaskErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.converter.CreateRequestTaskConverter;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.entity.request_task.RequestTask;
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

	@Transactional
	public Long createRequestTask(Long userId, RequestTaskCreateDto createDto) {

		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.USER_NOT_FOUND));

		RequestTask requestTask = createRequestTaskConverter.convertToRequestTask(createDto, writer);

		try {
			requestTaskRepository.save(requestTask);
			recruitmentService.createRecruitments(requestTask, createDto.getRecruitments());
		} catch (Exception e) {
			throw new BrainPixException(RequestTaskErrorCode.TASK_CREATION_FAILED);
		}

		return requestTask.getId();
	}

	@Transactional
	public void updateRequestTask(Long taskId, Long userId, RequestTaskUpdateDto updateDto) {
		RequestTask requestTask = requestTaskRepository.findById(taskId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.TASK_NOT_FOUND));

		// 작성자 검증 로직 추가
		requestTask.validateWriter(userId);

		// RequestTask 고유 필드 업데이트
		requestTask.updateRequestTaskFields(updateDto);

		try {
			requestTaskRepository.save(requestTask);
		} catch (Exception e) {
			throw new BrainPixException(RequestTaskErrorCode.TASK_UPDATE_FAILED);
		}
	}

	@Transactional
	public void deleteRequestTask(Long taskId, Long userId) {
		RequestTask requestTask = requestTaskRepository.findById(taskId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.TASK_NOT_FOUND));

		// 작성자 검증 로직 추가
		requestTask.validateWriter(userId);

		try {
			requestTaskRepository.delete(requestTask);
		} catch (Exception e) {
			throw new BrainPixException(RequestTaskErrorCode.TASK_DELETE_FAILED);
		}
	}
}
