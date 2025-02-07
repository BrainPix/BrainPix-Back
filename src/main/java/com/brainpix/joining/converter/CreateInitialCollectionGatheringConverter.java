package com.brainpix.joining.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.user.entity.User;

@Component
public class CreateInitialCollectionGatheringConverter {
	public CollectionGathering convertToInitialCollectionGathering(User joiner, CollaborationRecruitment recruitment) {
		return CollectionGathering.builder()
			.joiner(joiner)
			.accepted(true)
			.initialGathering(true)
			.collaborationRecruitment(recruitment)
			.build();
	}
}
