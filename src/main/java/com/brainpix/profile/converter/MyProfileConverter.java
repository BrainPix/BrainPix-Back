package com.brainpix.profile.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.entity.CompanyInformation;
import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.entity.Contact;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.User;

@Component
public class MyProfileConverter {

	public IndividualProfileResponseDto toDto(User user) {
		IndividualProfile profile = (IndividualProfile)user.getProfile();

		return IndividualProfileResponseDto.builder()
			.userId(user.getId())
			.userType(user.getUserType())
			.profileImage(user.getProfileImage())
			.specializations(profile.getSpecializationList().stream()
				.toList())
			.name(user.getName())
			.selfIntroduction(profile.getSelfIntroduction())
			.contacts(profile.getContacts().stream()
				.filter(Contact::getIsPublic)
				.map(contact -> IndividualProfileResponseDto.ContactDto.builder()
					.type(contact.getType())
					.value(contact.getValue())
					.build())
				.toList())
			.stacks(profile.getStacks().stream()
				.map(stack -> IndividualProfileResponseDto.StackDto.builder()
					.stackName(stack.getStackName())
					.proficiency(stack.getStackProficiency())
					.build())
				.collect(Collectors.toList()))
			.careers(profile.getCareers().stream()
				.map(career -> IndividualProfileResponseDto.CareerDto.builder()
					.content(career.getCareerContent())
					.startDate(career.getStartDate())
					.endDate(career.getEndDate())
					.build())
				.collect(Collectors.toList()))
			.build();
	}

	public CompanyProfileResponseDto toCompanyDto(Company user) {
		CompanyProfile profile = (CompanyProfile)user.getProfile();

		return CompanyProfileResponseDto.builder()
			.userId(user.getId())
			.userType(user.getUserType())
			.specializations(profile.getSpecializationList().stream()
				.toList())
			.name(user.getName())
			.imageUrl(user.getProfileImage())
			.selfIntroduction(profile.getSelfIntroduction())
			.businessInformation(profile.getBusinessInformation())
			.companyInformations(profile.getCompanyInformations().stream()
				.filter(CompanyInformation::getIsPublic)
				.map(info -> CompanyProfileResponseDto.CompanyInformationDto.builder()
					.type(info.getCompanyInformationType())
					.value(info.getValue())
					.isPublic(info.getIsPublic())
					.build())
				.collect(Collectors.toList()))
			.build();
	}
}
