package com.brainpix.profile.dto.careerdto;

import lombok.Data;

@Data
public class CareerUpdateRequest {
    private String content;
    private String startDate; // yyyy-MM-dd 형식
    private String endDate;
}