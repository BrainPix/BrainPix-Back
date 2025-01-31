package com.brainpix.profile.dto.response;

import java.time.LocalDateTime;

import com.brainpix.profile.entity.Portfolio;

public record PortfolioResponse(long id,
								String title,
								LocalDateTime createdDate,
								String profileImage
) {

	public static PortfolioResponse from(Portfolio portfolio) {
		return new PortfolioResponse(
			portfolio.getId(),
			portfolio.getTitle(),
			portfolio.getCreatedAt(),
			portfolio.getProfileImage()
		);
	}
}
