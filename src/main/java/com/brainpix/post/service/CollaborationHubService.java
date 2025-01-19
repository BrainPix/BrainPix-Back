package com.brainpix.post.service;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;

import com.brainpix.post.dto.CollaborationRecruitmentDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubService {

	private final CollaborationHubRepository collaborationHubRepository;
	private final UserRepository userRepository;

	public Long createCollaborationHub(CollaborationHubCreateDto createDto) {
		User writer = userRepository.findById(1L)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		CollaborationHub collaborationHub = CollaborationHub.builder()
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
			.link(createDto.getLink())
			.postAuth(createDto.getPostAuth())
			.build();

		if (createDto.getRecruitments() != null) {
			for (CollaborationRecruitmentDto recruitmentDto : createDto.getRecruitments()) {
				Gathering gathering = Gathering.builder()
					.totalQuantity(recruitmentDto.getTotalQuantity())
					.occupiedQuantity(recruitmentDto.getOccupiedQuantity())
					.build();

				CollaborationRecruitment recruitment = CollaborationRecruitment.builder()
					.parentCollaborationHub(collaborationHub)
					.domain(recruitmentDto.getDomain())
					.gathering(gathering)
					.build();
				collaborationHub.getRecruitments().add(recruitment);
			}
		}

		CollaborationHub savedTask = collaborationHubRepository.save(collaborationHub);
		return savedTask.getId();
	}

	public void updateCollaborationHub(Long id, CollaborationHubUpdateDto updateDto) {
		CollaborationHub existingTask = collaborationHubRepository.findById(id)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// CollaborationHub 고유 필드 업데이트
		existingTask.updateCollaborationHubFields(updateDto, updateDto.getRecruitments());

		collaborationHubRepository.save(existingTask);
	}

	public void deleteCollaborationHub(Long id) {
		if (!collaborationHubRepository.existsById(id)) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
		collaborationHubRepository.deleteById(id);
	}
}
