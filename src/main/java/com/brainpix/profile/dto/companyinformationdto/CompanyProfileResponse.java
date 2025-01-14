package com.brainpix.profile.dto.companyinformationdto;

import com.brainpix.profile.entity.Specialization;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyProfileResponse {
    private String businessType;         // 업종
    private String businessInformation; // 기업 정보
    private String homepage;            // 홈페이지 URL
    private Boolean openHomepage;       // 홈페이지 공개 여부
    private List<Specialization> specializations; // 전문 분야
}