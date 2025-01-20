package com.brainpix.post.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskRecruitmentDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.post.repository.RequestTaskRecruitmentRepository;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestTaskService {

	private final RequestTaskRepository requestTaskRepository;
	private final RequestTaskRecruitmentService recruitmentService;
	private final UserRepository userRepository;

	@Transactional
	public Long createRequestTask(Long userId, RequestTaskCreateDto createDto) {
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		RequestTask requestTask = RequestTask.builder()
			.writer(writer)
			//.writer(currentUser)
			.title(createDto.getTitle())
			.content(createDto.getContent())
			.specialization(createDto.getSpecialization())
			.openMyProfile(createDto.getOpenMyProfile())
			.viewCount(0L) // 초기 조회수는 0으로 설정
			.imageList(createDto.getImageList())
			.attachmentFileList(createDto.getAttachmentFileList())
			.deadline(createDto.getDeadline())
			.requestTaskType(createDto.getRequestTaskType())
			.postAuth(createDto.getPostAuth())
			.build();

		requestTaskRepository.save(requestTask);

		// 모집 정보 추가
		recruitmentService.createRecruitments(requestTask, createDto.getRecruitments());

		return requestTask.getId();
	}

	@Transactional
	public void updateRequestTask(Long taskid, RequestTaskUpdateDto updateDto) {
		RequestTask requestTask = requestTaskRepository.findById(taskid)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// RequestTask 고유 필드 업데이트
		requestTask.updateRequestTaskFields(updateDto);

		requestTaskRepository.save(requestTask);
	}

	@Transactional
	public void deleteRequestTask(Long id) {
		if (!requestTaskRepository.existsById(id)) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
		requestTaskRepository.deleteById(id);
	}
}
