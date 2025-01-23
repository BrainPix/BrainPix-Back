package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.post.dto.CollaborationHubProjectMemberDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationHubProjectMember;

@Component
public class CreateCollaborationHubProjectMemberConverter {

	public CollaborationHubProjectMember convertToProjectMember (CollaborationHub collaborationHub, CollaborationHubProjectMemberDto projectMemberDto) {
		return CollaborationHubProjectMember.builder()
			.collaborationHub(collaborationHub)
			.domain(projectMemberDto.getDomain())
			.build();
	}
}
