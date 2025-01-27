package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.post.dto.CollaborationHubProjectMemberDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;

@Component
public class CreateCollaborationHubProjectMemberConverter {

	public CollaborationRecruitment convertToProjectMember(CollaborationHub collaborationHub,
		CollaborationHubProjectMemberDto projectMemberDto) {
		return CollaborationRecruitment.builder()
			.parentCollaborationHub(collaborationHub)
			.domain(projectMemberDto.getDomain())
			.gathering(null)
			.build();
	}
}
