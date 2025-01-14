package com.brainpix.profile.service.profile;

import com.brainpix.profile.dto.TopInfoResponse;
import com.brainpix.profile.dto.individualinformationdto.IndividualProfileResponse;
import com.brainpix.profile.dto.individualinformationdto.IndividualProfileUpdateRequest;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.repository.IndividualProfileRepository;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.repository.IndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IndividualProfileService {

    private final IndividualProfileRepository profileRepository;
    private final IndividualRepository individualRepository;


    @Transactional(readOnly = true)
    public TopInfoResponse getTopInfo(Long userId) {
        Individual individual = individualRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return TopInfoResponse.builder()
            .userType("individual") // 개인 회원 유형
            .name(individual.getName()) // 회원 이름
            .profileImage(individual.getProfileImage()) // 프로필 이미지 URL
            .build();
    }

    @Transactional(readOnly = true)
    public IndividualProfileResponse getProfile(Long userId) {
        IndividualProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return IndividualProfileResponse.builder()
            .selfIntroduction(profile.getSelfIntroduction())
            .contactOpen(profile.getContactOpen())
            .careerOpen(profile.getCareerOpen())
            .stackOpen(profile.getStackOpen())
            .specializations(profile.getSpecializationList()) // 공개 여부는 필요 없으므로 반환
            .build();
    }

    @Transactional
    public void updateProfile(Long userId, IndividualProfileUpdateRequest request) {
        IndividualProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getSelfIntroduction() != null) {
            profile.updateSelfIntroduction(request.getSelfIntroduction());
        }
        if (request.getContactOpen() != null) {
            profile.updateContactOpen(request.getContactOpen());
        }
        if (request.getCareerOpen() != null) {
            profile.updateCareerOpen(request.getCareerOpen());
        }
        if (request.getStackOpen() != null) {
            profile.updateStackOpen(request.getStackOpen());
        }
    }
}
