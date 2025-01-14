package com.brainpix.profile.controller;

import com.brainpix.api.ApiResponse;
import com.brainpix.profile.dto.TopInfoResponse;
import com.brainpix.profile.dto.companyinformationdto.CompanyInformationResponse;
import com.brainpix.profile.dto.companyinformationdto.CompanyProfileResponse;
import com.brainpix.profile.dto.companyinformationdto.CompanyProfileUpdateRequest;
import com.brainpix.profile.dto.portfoliodto.PortfolioListResponse;
import com.brainpix.profile.service.profile.CompanyProfileService;
import com.brainpix.profile.service.portfolio.PortfolioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage/myinfo/company")
@RequiredArgsConstructor
public class CompanyInformationController {

    private final CompanyProfileService companyService;
    private final PortfolioService portfolioService;

    /**
     * 기업 회원 내 정보 전체 조회 (상단 + 중단)
     * @param userId 기업 회원 ID
     * @return 상단 및 중단 데이터를 포함한 마이페이지 데이터
     */
    @GetMapping
    public ApiResponse<CompanyInformationResponse> getCompleteCompanyMyPage(@RequestParam Long userId) {
        // 상단 데이터 조회
        TopInfoResponse topInfo = companyService.getTopInfo(userId);

        // 중단 데이터 조회
        CompanyProfileResponse profile = companyService.getProfile(userId);
        List<PortfolioListResponse> portfolios = portfolioService.getPortfolioList(userId);

        // 통합 응답 생성
        CompanyInformationResponse response = CompanyInformationResponse.builder()
            .topInfo(topInfo) // 상단 데이터
            .profile(profile) // 프로필 데이터
            .portfolios(portfolios) // 포트폴리오 리스트
            .build();

        return ApiResponse.success(response);
    }

    /**
     * 기업 회원 내 정보 수정
     * @param userId 기업 회원 ID
     * @param request 수정 요청 DTO
     */
    @PatchMapping
    public ApiResponse<Void> updateCompanyMyPage(@RequestParam Long userId, @RequestBody CompanyProfileUpdateRequest request) {
        companyService.updateProfile(userId, request);
        return ApiResponse.successWithNoData();
    }
}
