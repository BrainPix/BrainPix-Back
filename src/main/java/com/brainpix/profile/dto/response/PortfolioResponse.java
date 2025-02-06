package com.brainpix.profile.dto.response;

import java.time.LocalDate;

import com.brainpix.profile.entity.Portfolio;

public record PortfolioResponse(long id,
								String title,
								LocalDate createdDate,
								String profileImage
) {

	public static PortfolioResponse from(Portfolio portfolio) {
		return new PortfolioResponse(
			portfolio.getId(),
			portfolio.getTitle(),
			portfolio.getCreatedAt().toLocalDate(),
			portfolio.getProfileImage()
		);
	}
}
