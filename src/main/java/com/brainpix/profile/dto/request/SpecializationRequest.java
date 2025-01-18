package com.brainpix.profile.dto.request;

import com.brainpix.profile.entity.Specialization;

public record SpecializationRequest(String specialization) {

    public Specialization toDomain() {
        return Specialization.of(specialization);
    }
}
