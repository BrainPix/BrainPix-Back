package com.brainpix.profile.dto;

import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.profile.entity.ContactType;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.profile.entity.StackProficiency;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndividualProfileResponseDto {
	private Long userId;
	private String profileImage;
	private String userType; // 개인/기업
	private List<Specialization> specializations; // 전문 분야 (e.g., "IT/디자인")
	private String name; // 사용자 이름
	private String selfIntroduction; // 자기소개

	@Builder.Default
	private List<ContactDto> contacts = List.of(); // 기본값 빈 리스트

	@Builder.Default
	private List<StackDto> stacks = List.of();

	@Builder.Default
	private List<CareerDto> careers = List.of();

	@Getter
	@Builder
	public static class ContactDto {
		private ContactType type; // 연락처 유형
		private String value; // 연락처 값
	}

	@Getter
	@Builder
	public static class StackDto {
		private String stackName; // 스택 이름
		private StackProficiency proficiency; // 숙련도 (상/중/하)
	}

	@Getter
	@Builder
	public static class CareerDto {
		private String content; // 경력 내용
		@Schema(type = "string", example = "yyyy-MM")
		@DateTimeFormat(pattern = "yyyy-MM")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
		private YearMonth startDate; // 시작 날짜
		@Schema(type = "string", example = "yyyy-MM")
		@DateTimeFormat(pattern = "yyyy-MM")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
		private YearMonth endDate; // 종료 날짜
	}

}
