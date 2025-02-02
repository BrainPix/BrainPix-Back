package com.brainpix.profile.dto.request;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.profile.entity.Specialization;

public record SpecializationRequest(String specialization) {

	public Specialization toDomain() {
		if (specialization == null || specialization.trim().isEmpty()) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}

		try {
			return Specialization.of(specialization);
		} catch (BrainPixException e) {
			throw new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND);
		}
	}
}
