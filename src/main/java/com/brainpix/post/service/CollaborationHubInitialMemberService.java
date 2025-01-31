package com.brainpix.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.service.InitialCollectionGatheringService;
import com.brainpix.post.converter.CreateCollaborationHubInitialMemberConverter;
import com.brainpix.post.dto.CollaborationHubInitialMemberDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.repository.CollaborationHubRecruitmentRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubInitialMemberService {

	private final CollaborationHubRecruitmentRepository recruitmentRepository;
	private final UserRepository userRepository;
	private final CreateCollaborationHubInitialMemberConverter createProjectMemberConverter;
	private final InitialCollectionGatheringService initialCollectionGatheringService;

	@Transactional
	public void createInitialMembers(CollaborationHub collaborationHub,
		List<CollaborationHubInitialMemberDto> initialMemberDtos) {

		List<CollaborationRecruitment> projectMembers = new ArrayList<>();

		for (CollaborationHubInitialMemberDto initialMemberDto : initialMemberDtos) {

			User joiner = userRepository.findByIdentifier(initialMemberDto.getIdentifier())
				.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

			CollaborationRecruitment recruitment = createProjectMemberConverter.convertToInitialMember(
				collaborationHub, initialMemberDto);

			initialCollectionGatheringService.CreateInitialGathering(joiner, recruitment);

			projectMembers.add(recruitment);
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
