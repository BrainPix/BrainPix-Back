package com.brainpix.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.ProfileErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.profile.converter.ProfileConverter;
import com.brainpix.profile.dto.ProfileUpdateDto;
import com.brainpix.profile.entity.Career;
import com.brainpix.profile.entity.Contact;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.entity.Stack;
import com.brainpix.profile.repository.CareerRepository;
import com.brainpix.profile.repository.ContactRepository;
import com.brainpix.profile.repository.IndividualProfileRepository;
import com.brainpix.profile.repository.StackRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

	private final IndividualProfileRepository individualProfileRepository;
	private final UserRepository userRepository;

	private final ContactRepository contactRepository;
	private final StackRepository stackRepository;
	private final CareerRepository careerRepository;

	private final ProfileConverter converter;

	public void updateProfile(Long userId, ProfileUpdateDto updateDto) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));

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

	public String uploadProfileImage(Long userId, String imagePath) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));
		user.updateProfileImage(imagePath);
		return imagePath;
	}
}
