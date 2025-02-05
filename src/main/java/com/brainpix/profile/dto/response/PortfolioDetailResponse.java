package com.brainpix.profile.dto.response;

import java.time.YearMonth;
import java.util.List;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

public record PortfolioDetailResponse(
	long id,
	String title,
	List<Specialization> specializations,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	YearMonth startDate,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	YearMonth endDate,
	String content,
	String profileImage
) {
	public static PortfolioDetailResponse of(Portfolio portfolio) {
		List<Specialization> specs = portfolio.getSpecializationList();

		return new PortfolioDetailResponse(
			portfolio.getId(),
			portfolio.getTitle(),
			specs,
			portfolio.getStartDate(),
			portfolio.getEndDate(),
			portfolio.getContent(),
			portfolio.getProfileImage()
		);
	}

}