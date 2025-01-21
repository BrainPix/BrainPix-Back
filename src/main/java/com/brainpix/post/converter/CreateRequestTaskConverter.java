package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.user.entity.User;

@Component
public class CreateRequestTaskConverter {

	public RequestTask convertToRequestTask(RequestTaskCreateDto createDto, User writer) {
		return RequestTask.builder()
			.writer(writer)
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
	}
}
