package com.brainpix.joining.service;

import org.springframework.stereotype.Service;

import com.brainpix.joining.converter.CreateInitialGatheringConverter;
import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.repository.CollectionGatheringRepository;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InitialGatheringService {

	private final CollectionGatheringRepository collectionGatheringRepository;
	private final CreateInitialGatheringConverter createInitialGatheringConverter;

	@Transactional
	public void CreateInitialGathering(User joiner, CollaborationRecruitment recruitment) {
		CollectionGathering collectionGathering = createInitialGatheringConverter.convertToInitialGathering(
			joiner, recruitment);

		collectionGatheringRepository.save(collectionGathering);
	}
}
