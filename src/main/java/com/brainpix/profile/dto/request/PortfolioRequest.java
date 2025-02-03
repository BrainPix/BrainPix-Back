package com.brainpix.profile.dto.request;

import java.time.YearMonth;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.entity.Specialization;

public record PortfolioRequest(
	String title,
	Specialization specialization,
	YearMonth startDate,
	YearMonth endDate,
	String content,
	String profileImage
) {
	/**
	 * DTO -> Entity 변환 (생성 시 사용)
	 */
	public Portfolio toEntity(Profile profile) {
		return Portfolio.create(
			profile,
			title,
			specialization,
			startDate,
			endDate,
			content,
			profileImage
		);
	}

	/**
	 * 엔티티 수정에 반영할 메서드 (update)
	 */
	public void applyTo(Portfolio portfolio) {
		portfolio.update(
			title,
			specialization,
			startDate,
			endDate,
			content,
			profileImage
		);
	}
}