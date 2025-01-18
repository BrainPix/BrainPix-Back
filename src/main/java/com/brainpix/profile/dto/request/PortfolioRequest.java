package com.brainpix.profile.dto.request;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.entity.Specialization;
import java.time.YearMonth;
import java.util.List;

public record PortfolioRequest(
    String title,
    List<SpecializationRequest> specializations,
    YearMonth startDate,
    YearMonth endDate,
    String content
) {

    /**
     * DTO -> Entity 변환 (생성 시 사용)
     */
    public Portfolio toEntity(Profile profile) {
        // SpecializationRequest -> Specialization 변환
        List<Specialization> specs = specializations.stream()
            .map(SpecializationRequest::toDomain)  // ex) Specialization.of(...)
            .toList();

        return Portfolio.builder()
            .title(title)
            .specializationList(specs)
            .startDate(startDate)
            .endDate(endDate)
            .content(content)
            .profile(profile)
            .build();
    }

    /**
     * 엔티티 수정에 반영할 메서드 (update)
     */
    public void applyTo(Portfolio portfolio) {
        // SpecializationRequest -> Specialization 변환
        List<Specialization> specs = specializations.stream()
            .map(SpecializationRequest::toDomain)
            .toList();

        portfolio.changeTitle(title);
        portfolio.changeSpecializations(specs);
        portfolio.changeDates(startDate, endDate);
        portfolio.changeContent(content);
    }
}