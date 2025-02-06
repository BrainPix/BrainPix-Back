package com.brainpix.security.tokenManger;

import org.springframework.security.core.Authentication;

import com.brainpix.security.authenticationToken.BrainpixAuthenticationToken;

public interface TokenManager {
	BrainpixAuthenticationToken readAuthenticationToken(String token);

	String writeAuthenticationToken(Authentication authentication);

	String writeEmailAuthCodeToken(String email, String authCode);

	String readEmail(String token);
}
