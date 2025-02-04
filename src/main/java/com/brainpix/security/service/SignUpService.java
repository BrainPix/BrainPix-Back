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
		checkDuplicated(commonSignUpRequest.getId());
		checkDuplicatedNickName(commonSignUpRequest.myNickname());

		User user = commonSignUpRequest.toEntity(passwordEncoder.encode(commonSignUpRequest.getPassword()));
		userRepository.save(user);
		firstSignupProcess(user);
	}

	public void checkDuplicated(String identifier) {
		if (userRepository.findByIdentifier(identifier).isPresent()) {
			throw new BrainPixException(SecurityErrorCode.NICKNAME_DUPLICATED);
		}
	}

	public void checkDuplicatedNickName(String nickName) {
		if (userRepository.findByNickName(nickName).isPresent()) {
			throw new BrainPixException(SecurityErrorCode.NICKNAME_DUPLICATED);
		}
	}

	protected abstract void firstSignupProcess(User user);
}
