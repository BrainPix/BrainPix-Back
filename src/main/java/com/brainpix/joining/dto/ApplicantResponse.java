package com.brainpix.joining.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicantResponse {
    private Long applicantId;   // 지원자 ID
    private String applicantName; // 지원자 이름
    private Boolean accepted;     // 수락 여부
}
