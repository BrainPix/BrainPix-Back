package com.brainpix.profile.dto.response;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Specialization;

public record PortfolioDetailResponse(
	long id,
	String title,
	Specialization specialization, // 리스트 대신 단일 Enum 값
	String startDate,
	String endDate,
	String content,
	String profileImage
) {
	public static PortfolioDetailResponse of(Portfolio portfolio) {
		return new PortfolioDetailResponse(
			portfolio.getId(),
			portfolio.getTitle(),
			portfolio.getSpecialization(), // Enum 직접 사용
			portfolio.getStartDate().toString(),
			portfolio.getEndDate().toString(),
			portfolio.getContent(),
			portfolio.getProfileImage()
		);
	}
}