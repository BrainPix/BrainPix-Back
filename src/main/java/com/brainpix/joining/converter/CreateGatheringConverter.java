package com.brainpix.joining.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.GatheringDto;
import com.brainpix.joining.entity.quantity.Gathering;

@Component
public class CreateGatheringConverter {
	public Gathering convertToGathering(GatheringDto gatheringDto) {
		return Gathering.builder()
			.occupiedQuantity(0L)
			.totalQuantity(gatheringDto.getTotalQuantity())
			.build();
	}
}
