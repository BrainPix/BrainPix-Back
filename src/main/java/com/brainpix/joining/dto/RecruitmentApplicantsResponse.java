package com.brainpix.joining.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecruitmentApplicantsResponse {
    private Long recruitmentId;  // 모집 분야 ID
    private String domain;       // 모집 분야 이름
    private Long totalQuantity;  // 모집 총 인원
    private Long currentQuantity; // 현재 모집된 인원
    private List<ApplicantResponse> acceptedApplicants; // 수락된 지원자 리스트
    private List<ApplicantResponse> pendingApplicants;  // 대기 중 지원자 리스트

}