package com.brainpix.profile.dto;

import java.util.List;

import com.brainpix.profile.entity.CompanyInformationType;
import com.brainpix.profile.entity.Specialization;

import lombok.Getter;

@Getter
public class CompanyProfileUpdateDto {

	private String profileImage; // 프로필 이미지 경로
	private String selfIntroduction; // 기업 소개
	private String businessInformation; // 사업 정보

	private List<CompanyInformationDto> companyInformations; // 기업 정보
	private Boolean openInformation; // 기업 정보 공개 여부

	private List<Specialization> specializations; // 기업 분야 (최대 2개)

	@Getter
	public static class CompanyInformationDto {
		private CompanyInformationType type; // 기업 정보 타입
		private String value; // 기업 정보 값
	}
}