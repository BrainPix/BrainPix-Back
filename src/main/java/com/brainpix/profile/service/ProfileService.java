package com.brainpix.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.ProfileErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.profile.converter.MyProfileConverter;
import com.brainpix.profile.converter.ProfileConverter;
import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.CompanyProfileUpdateDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileUpdateDto;
import com.brainpix.profile.entity.Career;
import com.brainpix.profile.entity.CompanyInformation;
import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.profile.entity.Contact;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.entity.Stack;
import com.brainpix.profile.repository.CareerRepository;
import com.brainpix.profile.repository.CompanyInformationRepository;
import com.brainpix.profile.repository.CompanyProfileRepository;
import com.brainpix.profile.repository.ContactRepository;
import com.brainpix.profile.repository.IndividualProfileRepository;
import com.brainpix.profile.repository.StackRepository;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

	private final IndividualProfileRepository individualProfileRepository;
	private final CompanyProfileRepository companyProfileRepository;
	private final UserRepository userRepository;

	private final CompanyInformationRepository companyInformationRepository;

	private final ContactRepository contactRepository;
	private final StackRepository stackRepository;
	private final CareerRepository careerRepository;

	private final ProfileConverter converter;
	private final MyProfileConverter myconverter;

	public void updateIndividualProfile(Long userId, IndividualProfileUpdateDto updateDto) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));

		// 개인 소속인지 확인
		if (!(user instanceof Individual)) {
			throw new BrainPixException(ProfileErrorCode.INVALID_USER_TYPE);
		}

		IndividualProfile profile = individualProfileRepository.findByUser(user)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.PROFILE_NOT_FOUND));

		// 프로필 사진 업데이트
		if (updateDto.getProfileImage() != null) {
			user.updateProfileImage(updateDto.getProfileImage());
		}

		// 자기소개와 공개 여부 업데이트
		profile.update(updateDto.getSelfIntroduction(), updateDto.getContactOpen(),
			updateDto.getCareerOpen(), updateDto.getStackOpen());

		// 전문 분야 업데이트
		profile.updateSpecializations(updateDto.getSpecializations());

		// 연락처(Contact) 업데이트
		contactRepository.deleteByIndividualProfile(profile);
		List<Contact> contacts = converter.toContactList(updateDto.getContacts(), profile);
		contactRepository.saveAll(contacts);

		// 보유 기술(Stack) 업데이트
		stackRepository.deleteByIndividualProfile(profile);
		List<Stack> stacks = converter.toStackList(updateDto.getStacks(), profile);
		stackRepository.saveAll(stacks);

		// 경력 사항(Career) 업데이트
		careerRepository.deleteByIndividualProfile(profile);
		List<Career> careers = converter.toCareerList(updateDto.getCareers(), profile);
		careerRepository.saveAll(careers);
	}

	public void updateCompanyProfile(Long userId, CompanyProfileUpdateDto updateDto) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));

		// 기업 소속인지 확인
		if (!(user instanceof Company)) {
			throw new BrainPixException(ProfileErrorCode.INVALID_USER_TYPE);
		}

		CompanyProfile profile = companyProfileRepository.findByUser(user)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.PROFILE_NOT_FOUND));

		// 프로필 이미지 업데이트
		if (updateDto.getProfileImage() != null) {
			user.updateProfileImage(updateDto.getProfileImage());
		}

		// 기업 소개, 사업 정보 및 공개 여부 업데이트
		profile.update(updateDto.getSelfIntroduction(), updateDto.getBusinessInformation(),
			updateDto.getOpenInformation());

		// 기업 분야 업데이트
		profile.updateSpecializations(updateDto.getSpecializations());

		// 기업 정보 업데이트
		companyInformationRepository.deleteByCompanyProfile(profile);
		List<CompanyInformation> companyInformations = converter.toCompanyInformationList(
			updateDto.getCompanyInformations(), profile);
		companyInformationRepository.saveAll(companyInformations);
	}

	@Transactional(readOnly = true)
	public IndividualProfileResponseDto getMyProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));
		return myconverter.toDto(user);
	}

	@Transactional(readOnly = true)
	public CompanyProfileResponseDto getCompanyProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));

		if (!(user instanceof Company)) {
			throw new BrainPixException(ProfileErrorCode.INVALID_USER_TYPE);
		}

		return myconverter.toCompanyDto((Company)user);
	}

	/*public String uploadProfileImage(Long userId, String imagePath) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));
		user.updateProfileImage(imagePath);
		return imagePath;
	}*/
}
