package com.brainpix.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.brainpix.api.code.error.AuthorityErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.security.authenticationToken.BrainpixAuthenticationToken;
import com.brainpix.security.service.BrainpixUserDetailService;
import com.brainpix.security.userdetail.BrainpixUserDetails;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BrainpixUserAuthenticationProvider implements AuthenticationProvider {

	private final BrainpixUserDetailService brainpixUserDetailService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String identifier = authentication.getName();
		String password = (String)authentication.getCredentials();

		UserDetails userDetails = brainpixUserDetailService.loadUserByUsername(identifier);

		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			return new BrainpixAuthenticationToken(
				userDetails.getUsername(),
				userDetails.getPassword(),
				userDetails.getAuthorities(),
				((BrainpixUserDetails)userDetails).getDatabaseId());
		} else {
			throw new BrainPixException(AuthorityErrorCode.PASSWORD_NOT_MATCH);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return BrainpixAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
