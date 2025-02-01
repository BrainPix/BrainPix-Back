package com.brainpix.profile.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.User;

@Component
public class MyProfileConverter {

	public IndividualProfileResponseDto toDto(User user) {
		IndividualProfile profile = (IndividualProfile)user.getProfile();

		return IndividualProfileResponseDto.builder()
			.userType("개인")
			.specializations(profile.getSpecializationList().stream()
				.map(Enum::name)
				.collect(Collectors.joining("/"))) // 전문 분야를 '/'로 구분
			.name(user.getName())
			.selfIntroduction(profile.getSelfIntroduction())
			.contacts(profile.getContacts().stream()
				.map(contact -> IndividualProfileResponseDto.ContactDto.builder()
					.type(contact.getType().name())
					.value(contact.getValue())
					.build())
				.collect(Collectors.toList()))
			.stacks(profile.getStacks().stream()
				.map(stack -> IndividualProfileResponseDto.StackDto.builder()
					.stackName(stack.getStackName())
					.proficiency(stack.getStackProficiency().name())
					.build())
				.collect(Collectors.toList()))
			.careers(profile.getCareers().stream()
				.map(career -> IndividualProfileResponseDto.CareerDto.builder()
					.content(career.getCareerContent())
					.startDate(career.getStartDate().toString())
					.endDate(career.getEndDate().toString())
					.build())
				.collect(Collectors.toList()))
			.build();
	}

	public CompanyProfileResponseDto toCompanyDto(Company user) {
		CompanyProfile profile = (CompanyProfile)user.getProfile();

		return CompanyProfileResponseDto.builder()
			.userType("기업")
			.specializations(profile.getSpecializationList().stream()
				.map(Enum::name)
				.collect(Collectors.joining("/"))) // 기업 분야를 '/'로 구분
			.name(user.getName())
			.selfIntroduction(profile.getSelfIntroduction())
			.businessInformation(profile.getBusinessInformation())
			.companyInformations(profile.getCompanyInformations().stream()
				.map(info -> CompanyProfileResponseDto.CompanyInformationDto.builder()
					.type(info.getCompanyInformationType().name())
					.value(info.getValue())
					.build())
				.collect(Collectors.toList()))
			.build();
	}
}
