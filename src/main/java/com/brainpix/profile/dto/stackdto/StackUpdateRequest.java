package com.brainpix.profile.dto.stackdto;

import com.brainpix.profile.entity.StackProficiency;
import lombok.Data;

@Data
public class StackUpdateRequest {
    private String stackName;
    private StackProficiency proficiency;
}