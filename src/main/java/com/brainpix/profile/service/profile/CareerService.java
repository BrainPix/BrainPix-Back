package com.brainpix.profile.service.profile;

import com.brainpix.profile.dto.careerdto.CareerResponse;
import com.brainpix.profile.dto.careerdto.CareerUpdateRequest;
import com.brainpix.profile.entity.Career;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.repository.CareerRepository;
import com.brainpix.profile.repository.IndividualProfileRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CareerService {

    private final CareerRepository careerRepository;
    private final IndividualProfileRepository profileRepository;

    public CareerService(CareerRepository careerRepository, IndividualProfileRepository profileRepository) {
        this.careerRepository = careerRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional(readOnly = true)
    public List<CareerResponse> getCareers(Long userId) {
        IndividualProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 공개 여부 체크
        if (!profile.getCareerOpen()) {
            return List.of(); // 비공개인 경우 빈 리스트 반환
        }

        return careerRepository.findByUserId(userId).stream()
            .map(career -> CareerResponse.builder()
                .content(career.getCareerContent())
                .startDate(career.getStartDate().toString())
                .endDate(career.getEndDate().toString())
                .build())
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateCareers(Long userId, List<CareerUpdateRequest> requests) {
        careerRepository.deleteByUserId(userId);

        List<Career> careers = requests.stream()
            .map(request -> Career.builder()
                .careerContent(request.getContent())
                .startDate(YearMonth.parse(request.getStartDate()))
                .endDate(YearMonth.parse(request.getEndDate()))
                .build())
            .collect(Collectors.toList());

        careerRepository.saveAll(careers);
    }
}
