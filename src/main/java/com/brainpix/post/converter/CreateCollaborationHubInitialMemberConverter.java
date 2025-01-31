package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.post.dto.CollaborationHubInitialMemberDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;

@Component
public class CreateCollaborationHubInitialMemberConverter {

	public CollaborationRecruitment convertToInitialMember(CollaborationHub collaborationHub,
		CollaborationHubInitialMemberDto projectMemberDto) {
		return CollaborationRecruitment.builder()
			.parentCollaborationHub(collaborationHub)
			.domain(projectMemberDto.getDomain())
			.gathering(null)
			.build();
	}
}
