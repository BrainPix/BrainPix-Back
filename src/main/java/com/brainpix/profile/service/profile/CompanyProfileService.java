package com.brainpix.profile.service.profile;

import com.brainpix.profile.dto.TopInfoResponse;
import com.brainpix.profile.dto.companyinformationdto.CompanyProfileResponse;
import com.brainpix.profile.dto.companyinformationdto.CompanyProfileUpdateRequest;
import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.repository.CompanyProfileRepository;
import com.brainpix.user.entity.Company;
import com.brainpix.user.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyProfileService {

    private final CompanyProfileRepository profileRepository;
    private final CompanyRepository companyRepository;

    /**
     * 기업 회원 마이페이지 상단 데이터 조회
     * @param userId 기업 회원 ID
     * @return 상단 정보 응답 DTO
     */
    @Transactional(readOnly = true)
    public TopInfoResponse getTopInfo(Long userId) {
        Company company = companyRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return TopInfoResponse.builder()
            .userType("company") // 기업 회원 유형
            .name(company.getCompanyName()) // 기업명
            .profileImage(company.getProfileImage()) // 프로필 이미지 URL
            .build();
    }



    /**
     * 기업 프로필 조회
     * @param userId 기업 회원 ID
     * @return 기업 프로필 응답 DTO
     */
    @Transactional(readOnly = true)
    public CompanyProfileResponse getProfile(Long userId) {
        CompanyProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 공개 여부에 따라 데이터 처리
        if (Boolean.TRUE.equals(profile.getOpenHomepage())) {
            return CompanyProfileResponse.builder()
                .businessType(profile.getBusinessType())
                .businessInformation(profile.getBusinessInformation())
                .homepage(profile.getHomepage())
                .openHomepage(profile.getOpenHomepage())
                .specializations(profile.getSpecializationList())
                .build();
        } else {
            return CompanyProfileResponse.builder()
                .businessType(profile.getBusinessType())
                .businessInformation("비공개 정보입니다.")
                .homepage(null) // 홈페이지 정보를 숨김
                .openHomepage(profile.getOpenHomepage())
                .specializations(profile.getSpecializationList())
                .build();
        }
    }

    /**
     * 기업 프로필 업데이트
     * @param userId 기업 회원 ID
     * @param request 업데이트 요청 DTO
     */
    @Transactional
    public void updateProfile(Long userId, CompanyProfileUpdateRequest request) {
        CompanyProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 엔티티의 커스텀 업데이트 메서드 호출
        profile.updateProfile(
            request.getBusinessType(),
            request.getBusinessInformation(),
            request.getHomepage(),
            request.getOpenHomepage(),
            request.getSpecializations()
        );
    }
}
