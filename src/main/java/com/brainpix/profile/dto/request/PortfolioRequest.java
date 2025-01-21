package com.brainpix.profile.dto.request;

import java.time.YearMonth;
import java.util.List;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.entity.Specialization;

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
		List<Specialization> specs = specializations.stream()
			.map(SpecializationRequest::toDomain)  // ex) Specialization.of(...)
			.toList();

		return Portfolio.create(
			profile,
			title,
			specs,
			startDate,
			endDate,
			content
		);
	}

	/**
	 * 엔티티 수정에 반영할 메서드 (update)
	 */
	public void applyTo(Portfolio portfolio) {
		List<Specialization> specs = specializations.stream()
			.map(SpecializationRequest::toDomain)
			.toList();

		portfolio.update(
			title,
			specs,
			startDate,
			endDate,
			content
		);
	}
}