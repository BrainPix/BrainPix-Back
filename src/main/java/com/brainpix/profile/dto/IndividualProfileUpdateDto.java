package com.brainpix.profile.dto;

import java.util.List;

import com.brainpix.profile.entity.ContactType;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.profile.entity.StackProficiency;

import lombok.Getter;

@Getter
public class IndividualProfileUpdateDto {

	private String profileImage; // 프로필 이미지 경로
	private String selfIntroduction; // 자기소개

	private List<ContactDto> contacts; // 개별 정보
	private Boolean contactOpen; // 개별 정보 공개여부

	private List<StackDto> stacks; // 보유 기술
	private Boolean stackOpen; // 보유 기술 공개여부

	private List<CareerDto> careers; // 경력사항
	private Boolean careerOpen; // 경력사항 공개여부

	private List<Specialization> specializations; // 전문분야 (최대 2개)

	@Getter
	public static class ContactDto {
		private ContactType type; // 연락처 타입
		private String value; // 연락처 내용
	}

	@Getter
	public static class StackDto {
		private String name; // 스택 이름
		private StackProficiency proficiency; // 상/중/하
	}

	@Getter
	public static class CareerDto {
		private String content; // 경력 내용
		private String startDate; // 시작 날짜 (YYYY-MM)
		private String endDate; // 종료 날짜 (YYYY-MM)
	}
}
