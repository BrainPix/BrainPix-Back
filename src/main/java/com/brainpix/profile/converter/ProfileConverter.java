package com.brainpix.profile.converter;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.brainpix.profile.dto.CompanyProfileUpdateDto;
import com.brainpix.profile.dto.IndividualProfileUpdateDto;
import com.brainpix.profile.entity.Career;
import com.brainpix.profile.entity.CompanyInformation;
import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.entity.Contact;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.entity.Stack;

@Component
public class ProfileConverter {
	public Contact toContact(IndividualProfileUpdateDto.ContactDto dto, IndividualProfile profile) {
		return new Contact(dto.getType(), dto.getValue(), profile);
	}

	public Stack toStack(IndividualProfileUpdateDto.StackDto dto, IndividualProfile profile) {
		return new Stack(dto.getName(), dto.getProficiency(), profile);
	}

	public Career toCareer(IndividualProfileUpdateDto.CareerDto dto, IndividualProfile profile) {
		return new Career(dto.getContent(), YearMonth.parse(dto.getStartDate()), YearMonth.parse(dto.getEndDate()),
			profile);
	}

	public List<Contact> toContactList(List<IndividualProfileUpdateDto.ContactDto> dtoList, IndividualProfile profile) {
		return dtoList.stream().map(dto -> toContact(dto, profile)).collect(Collectors.toList());
	}

	public List<Stack> toStackList(List<IndividualProfileUpdateDto.StackDto> dtoList, IndividualProfile profile) {
		return dtoList.stream().map(dto -> toStack(dto, profile)).collect(Collectors.toList());
	}

	public List<Career> toCareerList(List<IndividualProfileUpdateDto.CareerDto> dtoList, IndividualProfile profile) {
		return dtoList.stream().map(dto -> toCareer(dto, profile)).collect(Collectors.toList());
	}

	public CompanyInformation toCompanyInformation(CompanyProfileUpdateDto.CompanyInformationDto dto,
		CompanyProfile profile) {
		return new CompanyInformation(dto.getType(), dto.getValue(), profile);
	}

	public List<CompanyInformation> toCompanyInformationList(
		List<CompanyProfileUpdateDto.CompanyInformationDto> dtoList, CompanyProfile profile) {
		return dtoList.stream()
			.map(dto -> toCompanyInformation(dto, profile))
			.collect(Collectors.toList());
	}
}
