package com.brainpix.profile.dto.request;

import com.brainpix.profile.entity.Specialization;

public record SpecializationRequest(Specialization specialization) {

	public Specialization toDomain() {
		return specialization;
	}
}
