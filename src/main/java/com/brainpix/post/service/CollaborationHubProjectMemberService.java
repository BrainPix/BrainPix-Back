package com.brainpix.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.CollaborationHubErrorCode;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.converter.CreateCollaborationHubProjectMemberConverter;
import com.brainpix.post.dto.CollaborationHubProjectMemberDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationHubProjectMember;
import com.brainpix.post.repository.CollaborationHubProjectMemberRepository;
import com.brainpix.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationHubProjectMemberService {

	private final CollaborationHubProjectMemberRepository memberRepository;
	private final UserRepository userRepository;
	private final CreateCollaborationHubProjectMemberConverter createProjectMemberConverter;

	@Transactional
	public void createProjectMembers(CollaborationHub collaborationHub, List<CollaborationHubProjectMemberDto> projectMemberDtos) {
		if (projectMemberDtos == null || projectMemberDtos.isEmpty()) {
			throw new BrainPixException(CollaborationHubErrorCode.INVALID_INPUT);
		}

		List<CollaborationHubProjectMember> members = new ArrayList<>();

		for (CollaborationHubProjectMemberDto dto : projectMemberDtos) {
			if (dto.getUserId() == null || dto.getDomain() == null) {
				throw new BrainPixException(CollaborationHubErrorCode.INVALID_REQUEST);
			}
		}

		for (CollaborationHubProjectMemberDto projectMemberDto : projectMemberDtos) {

			CollaborationHubProjectMember projectMember = createProjectMemberConverter.convertToProjectMember(collaborationHub, projectMemberDto);

			members.add(projectMember);
		}

		memberRepository.saveAll(members);
	}

	//개최 인원 아이디 검증
	public void validateUserId(Long userId) {
		boolean exists = userRepository.existsById(userId);

		if (!exists) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND); // "해당 ID의 유저를 찾을 수 없습니다.");
		}
	}
}
