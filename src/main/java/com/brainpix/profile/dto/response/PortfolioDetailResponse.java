package com.brainpix.profile.dto.response;

import java.util.List;

import com.brainpix.profile.entity.Portfolio;

public record PortfolioDetailResponse(
	long id,
	String title,
	List<String> specializations,
	String startDate,
	String endDate,
	String content,
	String profileImage
) {
	public static PortfolioDetailResponse of(Portfolio portfolio) {
		List<String> specs = portfolio.getSpecializationList().stream()
			.map(Enum::name)
			.toList();

		return new PortfolioDetailResponse(
			portfolio.getId(),
			portfolio.getTitle(),
			specs,
			portfolio.getStartDate().toString(),
			portfolio.getEndDate().toString(),
			portfolio.getContent(),
			portfolio.getProfileImage()
		);
	}

}