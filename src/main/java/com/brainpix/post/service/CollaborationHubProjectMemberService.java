package com.brainpix.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CollaborationHubErrorCode;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.service.InitialGatheringService;
import com.brainpix.post.converter.CreateCollaborationHubProjectMemberConverter;
import com.brainpix.post.dto.CollaborationHubProjectMemberDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.CollaborationHubRecruitmentRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubProjectMemberService {

	private final CollaborationHubRecruitmentRepository recruitmentRepository;
	private final UserRepository userRepository;
	private final CreateCollaborationHubProjectMemberConverter createProjectMemberConverter;
	private final InitialGatheringService initialGatheringService;

	@Transactional
	public void createProjectMembers(CollaborationHub collaborationHub,
		List<CollaborationHubProjectMemberDto> projectMemberDtos) {
		if (projectMemberDtos == null || projectMemberDtos.isEmpty()) {
			throw new BrainPixException(CollaborationHubErrorCode.INVALID_INPUT);
		}

		List<CollaborationRecruitment> projectMembers = new ArrayList<>();

		for (CollaborationHubProjectMemberDto projectMemberDto : projectMemberDtos) {
			if (projectMemberDto.getDomain() == null) {
				throw new BrainPixException(CollaborationHubErrorCode.INVALID_REQUEST);
			}
		}

		for (CollaborationHubProjectMemberDto projectMemberDto : projectMemberDtos) {

			CollaborationRecruitment projectMember = createProjectMemberConverter.convertToProjectMember(
				collaborationHub, projectMemberDto);

			User joiner = userRepository.findByIdentifier(projectMemberDto.getIdentifier())
				.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

			initialGatheringService.CreateInitialGathering(joiner, projectMember);

			projectMembers.add(projectMember);
		}

		recruitmentRepository.saveAll(projectMembers);

	}

	//개최 인원 아이디 검증
	public void validateUserIdentifier(String identifier) {
		boolean exists = userRepository.existsByIdentifier(identifier);

		if (!exists) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND); // "해당 ID의 유저를 찾을 수 없습니다.");
		}
	}
}
