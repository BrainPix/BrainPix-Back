package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.user.entity.User;

@Component
public class CreateCollaborationHubConverter {

	public CollaborationHub convertToCollaborationHub(CollaborationHubCreateDto createDto, User writer) {
		return CollaborationHub.builder()
			.writer(writer)
			.title(createDto.getTitle())
			.content(createDto.getContent())
			.specialization(createDto.getSpecialization())
			.openMyProfile(createDto.getOpenMyProfile())
			.viewCount(0L) // 초기 조회수는 0으로 설정
			.imageList(createDto.getImageList())
			.attachmentFileList(createDto.getAttachmentFileList())
			.deadline(createDto.getDeadline())
			.link(createDto.getLink())
			.postAuth(createDto.getPostAuth())
			.build();
	}
}
