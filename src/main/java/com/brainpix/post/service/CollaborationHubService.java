package com.brainpix.post.service;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CollaborationHubErrorCode;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.RequestTaskErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.converter.CreateCollaborationHubConverter;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubService {

	private final CollaborationHubRepository collaborationHubRepository;
	private final CollaborationHubRecruitmentService recruitmentService;
	private final UserRepository userRepository;
	private final CreateCollaborationHubConverter createCollaborationHubConverter;
	private final CollaborationHubProjectMemberService collaborationHubProjectMemberService;

	@Transactional
	public Long createCollaborationHub(Long userId, CollaborationHubCreateDto createDto) {

		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.USER_NOT_FOUND));

		CollaborationHub collaborationHub = createCollaborationHubConverter.convertToCollaborationHub(createDto, writer);

		try {
			collaborationHubRepository.save(collaborationHub);
			recruitmentService.createRecruitments(collaborationHub, createDto.getRecruitments());
			collaborationHubProjectMemberService.createProjectMembers(collaborationHub, createDto.getMembers());
		} catch (Exception e) {
			throw new BrainPixException(CollaborationHubErrorCode.TASK_CREATION_FAILED);
		}

		return collaborationHub.getId();
	}

	@Transactional
	public void updateCollaborationHub(Long workspaceId, Long userId, CollaborationHubUpdateDto updateDto) {
		CollaborationHub collaboration = collaborationHubRepository.findById(workspaceId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 검증 로직 추가
		collaboration.validateWriter(userId);

		// CollaborationHub 고유 필드 업데이트
		collaboration.updateCollaborationHubFields(updateDto);

		try {
			collaborationHubRepository.save(collaboration);
		} catch (Exception e) {
			throw new BrainPixException(CollaborationHubErrorCode.TASK_UPDATE_FAILED);
		}
	}

	@Transactional
	public void deleteCollaborationHub(Long workspaceId, Long userId) {
		CollaborationHub collaboration = collaborationHubRepository.findById(workspaceId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 검증 로직 추가
		collaboration.validateWriter(userId);

		collaborationHubRepository.deleteById(workspaceId);

		try {
			collaborationHubRepository.deleteById(workspaceId);
		} catch (Exception e) {
			throw new BrainPixException(CollaborationHubErrorCode.TASK_DELETE_FAILED);
		}
	}
}
