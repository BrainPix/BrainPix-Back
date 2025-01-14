package com.brainpix.profile.dto.companyinformationdto;

import com.brainpix.profile.dto.TopInfoResponse;
import com.brainpix.profile.dto.portfoliodto.PortfolioListResponse;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyInformationResponse {
    private TopInfoResponse topInfo;
    private CompanyProfileResponse profile;
    private List<PortfolioListResponse> portfolios; // 포트폴리오 추가
}
