package com.brainpix.profile.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopInfoResponse {
    private String userType; // 회원 유형 (individual, company)
    private String name;     // 이름 (개인: 이름, 기업: 기업명)
    private String profileImage; // 프로필 이미지 URL
}