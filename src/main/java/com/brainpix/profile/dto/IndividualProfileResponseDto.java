package com.brainpix.profile.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndividualProfileResponseDto {
	private String userType; // 개인/기업
	private String specializations; // 전문 분야 (e.g., "IT/디자인")
	private String name; // 사용자 이름
	private String selfIntroduction; // 자기소개

	private List<ContactDto> contacts; // 개별 정보
	private List<StackDto> stacks; // 보유 기술
	private List<CareerDto> careers; // 경력 사항
	private List<PortfolioDto> portfolios; // 포트폴리오

	@Getter
	@Builder
	public static class ContactDto {
		private String type; // 연락처 유형
		private String value; // 연락처 값
	}

	@Getter
	@Builder
	public static class StackDto {
		private String stackName; // 스택 이름
		private String proficiency; // 숙련도 (상/중/하)
	}

	@Getter
	@Builder
	public static class CareerDto {
		private String content; // 경력 내용
		private String startDate; // 시작 날짜
		private String endDate; // 종료 날짜
	}

	@Getter
	@Builder
	public static class PortfolioDto {
		private String title; // 포트폴리오 제목
		private String imageUrl; //포트폴리오 썸네일
		private String createdDate; // 작성일
	}
}
