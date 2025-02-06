package com.brainpix.profile.dto;

import java.util.List;

import com.brainpix.profile.entity.CompanyInformationType;
import com.brainpix.profile.entity.Specialization;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyProfileResponseDto {
	private String userType; // 개인/기업
	private List<Specialization> specializations;
	private String name; // 기업 이름
	private String selfIntroduction; // 기업 소개
	private String businessInformation; // 사업 정보

	private List<CompanyInformationDto> companyInformations; // 기업 정보

	@Getter
	@Builder
	public static class CompanyInformationDto {
		private CompanyInformationType type; // 기업 정보 타입
		private String value; // 기업 정보 값
	}

}
