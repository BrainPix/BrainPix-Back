package com.brainpix.profile.dto;

import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.profile.entity.ContactType;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.profile.entity.StackProficiency;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class IndividualProfileUpdateDto {

	private String profileImage; // 프로필 이미지 경로
	private String selfIntroduction; // 자기소개

	private List<ContactDto> contacts; // 개별 정보

	private List<StackDto> stacks; // 보유 기술
	private Boolean stackOpen; // 보유 기술 공개여부

	private List<CareerDto> careers; // 경력사항
	private Boolean careerOpen; // 경력사항 공개여부

	private List<Specialization> specializations; // 전문분야 (최대 2개)

	@Getter
	public static class ContactDto {
		private ContactType type; // 연락처 타입
		private String value; // 연락처 내용
		private Boolean isPublic;
	}

	@Getter
	public static class StackDto {
		private String name; // 스택 이름
		private StackProficiency proficiency; // 상/중/하
	}

	@Getter
	public static class CareerDto {
		private String content; // 경력 내용
		@Schema(type = "string", example = "yyyy-MM")
		@DateTimeFormat(pattern = "yyyy-MM")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
		private YearMonth startDate; // 시작 날짜 (YYYY-MM)
		@Schema(type = "string", example = "yyyy-MM")
		@DateTimeFormat(pattern = "yyyy-MM")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
		private YearMonth endDate; // 종료 날짜 (YYYY-MM)
	}
}
