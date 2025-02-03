package com.brainpix.joining.service;

import org.springframework.stereotype.Service;

import com.brainpix.joining.converter.CreateGatheringConverter;
import com.brainpix.joining.dto.GatheringDto;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.joining.repository.GatheringRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {
	private final GatheringRepository gatheringRepository;
	private final CreateGatheringConverter createGatheringConverter;

	@Transactional
	public Gathering createGathering(GatheringDto gatheringDto) {

		Gathering gathering = createGatheringConverter.convertToGathering(gatheringDto);

		return gatheringRepository.save(gathering);
	}
}
