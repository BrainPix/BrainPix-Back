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
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestTaskService {

	private final RequestTaskRepository requestTaskRepository;
	private final UserRepository userRepository;

	public Long createRequestTask(RequestTaskCreateDto createDto) {
		User writer = userRepository.findById(1L)
		//Individual writer = (Individual) userRepository.findById(1L)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		RequestTask requestTask = RequestTask.builder()
			.writer(writer)
			//.writer(currentUser)
			.title(createDto.getTitle())
			.content(createDto.getContent())
			.category(createDto.getCategory())
			.openMyProfile(createDto.getOpenMyProfile())
			.viewCount(0L) // 초기 조회수는 0으로 설정
			.imageList(createDto.getImageList())
			.attachmentFileList(createDto.getAttachmentFileList())
			.deadline(createDto.getDeadline())
			.collaborationType(createDto.getCollaborationType())
			.ideaMarketAuth(createDto.getIdeaMarketAuth())
			.build();

		if (createDto.getRecruitments() != null) {
			for (RequestTaskRecruitmentDto recruitmentDto : createDto.getRecruitments()) {
				Price price = Price.builder()
					.totalQuantity(recruitmentDto.getTotalQuantity())
					.occupiedQuantity(recruitmentDto.getOccupiedQuantity())
					.price(recruitmentDto.getPrice())
					.paymentDuration(recruitmentDto.getPaymentDuration())
					.build();

				RequestTaskRecruitment recruitment = RequestTaskRecruitment.builder()
					.requestTask(requestTask)
					.domain(recruitmentDto.getDomain())
					.price(price)
					.build();
				requestTask.getRecruitments().add(recruitment);
			}
		}

		RequestTask savedTask = requestTaskRepository.save(requestTask);
		return savedTask.getId();
	}

	public void updateRequestTask(Long id, RequestTaskUpdateDto updateDto) {
		RequestTask existingTask = requestTaskRepository.findById(id)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// RequestTask 고유 필드 업데이트
		existingTask.updateRequestTaskFields(updateDto, updateDto.getRecruitments());

		requestTaskRepository.save(existingTask);
	}

	public void deleteRequestTask(Long id) {
		if (!requestTaskRepository.existsById(id)) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
		requestTaskRepository.deleteById(id);
	}
}
