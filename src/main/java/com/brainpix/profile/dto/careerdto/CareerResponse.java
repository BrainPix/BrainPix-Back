package com.brainpix.profile.dto.careerdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CareerResponse {
    private String content;
    private String startDate;
    private String endDate;
}