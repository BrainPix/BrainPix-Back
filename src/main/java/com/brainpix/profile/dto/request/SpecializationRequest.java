package com.brainpix.profile.dto.request;

import com.brainpix.profile.entity.Specialization;

public record SpecializationRequest(String specialization) {

	public Specialization toDomain() {
		if (specialization == null || specialization.trim().isEmpty()) {
			throw new IllegalArgumentException("Specialization null 이나 빈 값이면 안됩니다.");
		}

		try {
			return Specialization.of(specialization);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("유효하지 않은 값입니다 " + specialization, e);
		}
	}
}
