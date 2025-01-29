package com.brainpix.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.ProfileErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.profile.converter.MyProfileConverter;
import com.brainpix.profile.dto.MyProfileResponseDto;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyProfileService {

	private final UserRepository userRepository;
	private final MyProfileConverter converter;

	@Transactional(readOnly = true)
	public MyProfileResponseDto getMyProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(ProfileErrorCode.USER_NOT_FOUND));
		return converter.toDto(user);
	}
}