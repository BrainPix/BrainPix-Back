package com.brainpix.profile.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyProfileResponseDto {
	private String userType; // 개인/기업
	private String specializations; // 기업 분야 (e.g., "IT/디자인")
	private String name; // 기업 이름
	private String selfIntroduction; // 기업 소개
	private String businessInformation; // 사업 정보

	private List<CompanyInformationDto> companyInformations; // 기업 정보
	private List<PortfolioDto> portfolios; // 포트폴리오

	private List<PublicProfileResponseDto.PostPreviewDto> postHistory;

	@Getter
	@Builder
	public static class CompanyInformationDto {
		private String type; // 기업 정보 타입
		private String value; // 기업 정보 값
	}

	@Getter
	@Builder
	public static class PortfolioDto {
		private String title; // 포트폴리오 제목
		private String imageUrl; //포트폴리오 썸네일
		private String createdDate; // 작성일
	}
}
