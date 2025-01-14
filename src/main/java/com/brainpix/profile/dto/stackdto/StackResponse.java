package com.brainpix.profile.dto.stackdto;

import com.brainpix.profile.entity.StackProficiency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StackResponse {
    private String stackName;
    private StackProficiency proficiency;
}