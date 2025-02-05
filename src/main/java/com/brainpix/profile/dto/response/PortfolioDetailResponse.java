package com.brainpix.profile.dto.response;

import java.util.List;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Specialization;

public record PortfolioDetailResponse(
	long id,
	String title,
	List<Specialization> specializations,
	String startDate,
	String endDate,
	String content,
	String profileImage
) {
	public static PortfolioDetailResponse of(Portfolio portfolio) {
		List<Specialization> specs = portfolio.getSpecializationList();

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