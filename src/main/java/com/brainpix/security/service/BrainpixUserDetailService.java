package com.brainpix.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.security.userdetail.BrainpixUserDetails;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BrainpixUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userRepository.findByIdentifier(username)
			.map(BrainpixUserDetails::new)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
	}
}
