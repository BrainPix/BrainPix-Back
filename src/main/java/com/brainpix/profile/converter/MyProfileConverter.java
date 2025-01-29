package com.brainpix.profile.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.brainpix.profile.dto.MyProfileResponseDto;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.user.entity.User;

@Component
public class MyProfileConverter {

	public MyProfileResponseDto toDto(User user) {
		IndividualProfile profile = (IndividualProfile)user.getProfile();

		return MyProfileResponseDto.builder()
			.userType("개인")
			.specializations(profile.getSpecializationList().stream()
				.map(Enum::name)
				.collect(Collectors.joining("/"))) // 전문 분야를 '/'로 구분
			.name(user.getName())
			.selfIntroduction(profile.getSelfIntroduction())
			.contacts(profile.getContacts().stream()
				.map(contact -> MyProfileResponseDto.ContactDto.builder()
					.type(contact.getType().name())
					.value(contact.getValue())
					.build())
				.collect(Collectors.toList()))
			.stacks(profile.getStacks().stream()
				.map(stack -> MyProfileResponseDto.StackDto.builder()
					.stackName(stack.getStackName())
					.proficiency(stack.getStackProficiency().name())
					.build())
				.collect(Collectors.toList()))
			.careers(profile.getCareers().stream()
				.map(career -> MyProfileResponseDto.CareerDto.builder()
					.content(career.getCareerContent())
					.startDate(career.getStartDate().toString())
					.endDate(career.getEndDate().toString())
					.build())
				.collect(Collectors.toList()))
			.portfolios(profile.getPortfolios().stream()
				.map(portfolio -> MyProfileResponseDto.PortfolioDto.builder()
					.title(portfolio.getTitle())
					.content(portfolio.getContent())
					.createdDate(portfolio.getCreatedAt().toString())
					.build())
				.collect(Collectors.toList()))
			.build();
	}
}
