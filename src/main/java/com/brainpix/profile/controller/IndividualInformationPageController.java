package com.brainpix.profile.controller;

import com.brainpix.api.ApiResponse;

import com.brainpix.profile.dto.TopInfoResponse;
import com.brainpix.profile.dto.careerdto.CareerResponse;
import com.brainpix.profile.dto.contactdto.ContactResponse;
import com.brainpix.profile.dto.individualinformationdto.IndividualProfileResponse;
import com.brainpix.profile.dto.individualinformationdto.InformationCompleteResponse;
import com.brainpix.profile.dto.individualinformationdto.InformationPageResponse;

import com.brainpix.profile.dto.individualinformationdto.InformationUpdateRequest;
import com.brainpix.profile.dto.portfoliodto.PortfolioListResponse;
import com.brainpix.profile.dto.stackdto.StackResponse;
import com.brainpix.profile.service.profile.CareerService;
import com.brainpix.profile.service.profile.ContactService;
import com.brainpix.profile.service.profile.IndividualProfileService;
import com.brainpix.profile.service.portfolio.PortfolioService;
import com.brainpix.profile.service.profile.StackService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage/myinfo/individual")
@RequiredArgsConstructor
public class IndividualInformationPageController {

    private final IndividualProfileService profileService;
    private final StackService stackService;
    private final PortfolioService portfolioService;
    private final CareerService careerService;
    private final ContactService contactService;

    /**
     * 내 정보 전체 조회 (상단 + 중단)
     * @param userId 사용자 ID
     * @return 내 정보 데이터
     */
    @GetMapping("/users/{userId}")
    public ApiResponse<InformationCompleteResponse> getCompleteMyPage(@PathVariable Long userId) {
        // 상단 정보 조회
        TopInfoResponse topInfo = profileService.getTopInfo(userId);

        // 중단 정보 조회
        IndividualProfileResponse profile = profileService.getProfile(userId);
        List<StackResponse> stacks = stackService.getStacks(userId);
        List<PortfolioListResponse> portfolios = portfolioService.getPortfolioList(userId); // 수정된 호출 방식
        List<CareerResponse> careers = careerService.getCareers(userId);
        List<ContactResponse> contacts = contactService.getContacts(userId);

        // 통합 데이터 생성
        InformationCompleteResponse response = InformationCompleteResponse.builder()
            .topInfo(topInfo) // 상단 데이터
            .middleContent(InformationPageResponse.builder() // 중단 데이터
                .profile(profile)
                .stacks(stacks)
                .portfolios(portfolios)
                .careers(careers)
                .contacts(contacts)
                .build())
            .build();

        return ApiResponse.success(response);
    }

    /**
     * 일반 회원 내 정보 수정
     * @param userId 회원 ID
     * @param request 수정 요청 데이터
     */
    @PatchMapping("/users/{userId}")
    public ApiResponse<Void> updateIndividualMyPage(
        @PathVariable Long userId,
        @RequestBody InformationUpdateRequest request
    ) {
        profileService.updateProfile(userId, request.getProfile());
        stackService.updateStacks(userId, request.getStacks());
        careerService.updateCareers(userId, request.getCareers());
        contactService.updateContacts(userId, request.getContacts());
        return ApiResponse.successWithNoData();
    }
}
