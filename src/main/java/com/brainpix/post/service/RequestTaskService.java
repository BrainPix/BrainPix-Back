package com.brainpix.post.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskRecruitmentDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestTaskService {

	private final RequestTaskRepository requestTaskRepository;

	// Mock 사용자 가져오기 (테스트용)
	// private User getMockUser() {
	// 	Individual mockUser = Individual.builder()
	// 		.identifier("testuser")
	// 		.name("Test User")
	// 		.email("testuser@example.com")
	// 		.profile(null) // 필요에 따라 설정
	// 		.build();
	//
	// 	try {
	// 		Field idField = User.class.getDeclaredField("id");
	// 		idField.setAccessible(true);
	// 		idField.set(mockUser, 1L); // id 강제로 설정
	// 	} catch (Exception e) {
	// 		throw new RuntimeException("Mock User 생성 중 오류 발생", e);
	// 	}
	//
	// 	return mockUser;
	// }
	private final UserRepository userRepository;

	public Long createRequestTask(RequestTaskCreateDto createDto) {
		Individual writer = (Individual) userRepository.findById(1L)
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
				RequestTaskRecruitment recruitment = RequestTaskRecruitment.builder()
					.requestTask(requestTask)
					.domain(recruitmentDto.getDomain())
					.price(recruitmentDto.getPrice())
					.currentPeople(recruitmentDto.getCurrentPeople())
					.totalPeople(recruitmentDto.getTotalPeople())
					.agreementType(recruitmentDto.getAgreementType())
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
		existingTask.updateRequestTaskFields(updateDto);

		// 모집 업데이트
		if (updateDto.getRecruitments() != null) {
			existingTask.updateRecruitmentFields(updateDto.getRecruitments());
		}

		requestTaskRepository.save(existingTask);
	}

	public void deleteRequestTask(Long id) {
		if (!requestTaskRepository.existsById(id)) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
		requestTaskRepository.deleteById(id);
	}
}
