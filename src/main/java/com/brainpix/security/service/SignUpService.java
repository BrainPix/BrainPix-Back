package com.brainpix.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.SecurityErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.security.dto.request.SignUpRequest;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public abstract class SignUpService {

	private final UserRepository userRepository;
	public final PasswordEncoder passwordEncoder;

	public void signUpUser(SignUpRequest.CommonSignUpRequest commonSignUpRequest) {
		if (isDuplicated(commonSignUpRequest.getId())) {
			throw new BrainPixException(SecurityErrorCode.IDENTIFIER_DUPLICATED);
		}
		if (isDuplicatedNickName(commonSignUpRequest.getUserNickName())) {
			throw new BrainPixException(SecurityErrorCode.NICKNAME_DUPLICATED);
		}

		User user = commonSignUpRequest.toEntity(passwordEncoder.encode(commonSignUpRequest.getPassword()));
		userRepository.save(user);
		firstSignupProcess(user);
	}

	public boolean isDuplicated(String identifier) {
		return userRepository.findByIdentifier(identifier).isPresent();
	}

	public boolean isDuplicatedNickName(String nickName) {
		return userRepository.findByNickName(nickName).isPresent();
	}

	protected abstract void firstSignupProcess(User user);
}
