package com.brainpix.security.tokenManger;

import org.springframework.security.core.Authentication;

import com.brainpix.security.authenticationToken.BrainpixAuthenticationToken;

public interface TokenManager {
	BrainpixAuthenticationToken readToken(String token);

	String writeToken(Authentication authentication);
}
