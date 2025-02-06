package com.brainpix.profile.dto.request;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PortfolioRequest(
	@NotBlank(message = "포트폴리오 제목을 입력해주세요.")
	String title,
	List<Specialization> specializations,
	@NotBlank(message = "시작 날짜를 입력해주세요.")
	@Schema(type = "string", example = "yyyy-MM")
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	YearMonth startDate,
	@NotBlank(message = "종료 날짜를 입력해주세요.")
	@Schema(type = "string", example = "yyyy-MM")
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	YearMonth endDate,
	@NotBlank(message = "포트폴리오 내용을 입력해주세요.")
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
			specializations,
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
			Optional.ofNullable(title).orElse(portfolio.getTitle()),  // null이면 기존 값 유지
			Optional.ofNullable(specializations).orElse(portfolio.getSpecializationList()),  // null이면 기존 값 유지
			Optional.ofNullable(startDate).orElse(portfolio.getStartDate()),
			Optional.ofNullable(endDate).orElse(portfolio.getEndDate()),
			Optional.ofNullable(content).orElse(portfolio.getContent()),
			Optional.ofNullable(profileImage).orElse(portfolio.getProfileImage())
		);
	}
}