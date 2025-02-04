package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.post.dto.CollaborationRecruitmentDto;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;

@Component
public class CreateCollaborationHubRecruitmentConverter {

	public CollaborationRecruitment convertToCollaborationRecruitment(CollaborationHub collaborationHub, CollaborationRecruitmentDto recruitmentDto, Gathering gathering) {
		return CollaborationRecruitment.builder()
			.parentCollaborationHub(collaborationHub)
			.domain(recruitmentDto.getDomain())
			.gathering(gathering)
			.build();
	}
}
