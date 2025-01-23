package com.brainpix.post.converter;

import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.post.dto.CollaborationHubProjectMemberDto;
import com.brainpix.post.dto.CollaborationRecruitmentDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.entity.collaboration_hub.CollaborationHubProjectMember;

public class CreateCollaborationHubProjectMemberConverter {

	public CollaborationHubProjectMember convertToProjectMember (CollaborationHub collaborationHub, CollaborationHubProjectMemberDto projectMemberDto) {
		return CollaborationHubProjectMember.builder()
			.collaborationHub(collaborationHub)
			.domain(projectMemberDto.getDomain())
			.build();
	}
}
