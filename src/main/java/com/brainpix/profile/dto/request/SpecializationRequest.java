package com.brainpix.profile.dto.request;

import com.brainpix.profile.entity.Specialization;

import jakarta.validation.constraints.NotNull;

public record SpecializationRequest(@NotNull(message = "전문 분야는 필수 입력값입니다.") Specialization specialization) {

	public Specialization toDomain() {
		return specialization;
	}
}
