package com.brainpix.profile.dto.response;

import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record PortfolioDetailResponse(
	long id,
	String title,
	List<Specialization> specializations,
	@Schema(type = "string", example = "yyyy-MM")
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	YearMonth startDate,

	@Schema(type = "string", example = "yyyy-MM")
	@DateTimeFormat(pattern = "yyyy-MM")
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