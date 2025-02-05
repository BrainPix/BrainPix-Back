package com.brainpix.profile.dto.request;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;

public record PortfolioRequest(
	@NotBlank(message = "포트폴리오 제목을 입력해주세요.")
	String title,
	List<SpecializationRequest> specializations,
	@NotBlank(message = "시작 날짜를 입력해주세요.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	YearMonth startDate,
	@NotBlank(message = "종료 날짜를 입력해주세요.")
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
		List<Specialization> specs = Optional.ofNullable(specializations)
			.orElse(List.of())
			.stream()
			.map(SpecializationRequest::toDomain)
			.toList();

		return Portfolio.create(
			profile,
			title,
			specs,
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
		List<Specialization> specs = Optional.ofNullable(specializations)
			.orElse(List.of())
			.stream()
			.map(SpecializationRequest::toDomain)
			.toList();

		portfolio.update(
			Optional.ofNullable(title).orElse(portfolio.getTitle()),  // null이면 기존 값 유지
			specs,
			Optional.ofNullable(startDate).orElse(portfolio.getStartDate()),
			Optional.ofNullable(endDate).orElse(portfolio.getEndDate()),
			Optional.ofNullable(content).orElse(portfolio.getContent()),
			Optional.ofNullable(profileImage).orElse(portfolio.getProfileImage())
		);
	}
}