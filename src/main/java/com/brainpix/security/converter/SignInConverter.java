package com.brainpix.security.converter;

import com.brainpix.security.authenticationToken.BrainpixAuthenticationToken;
import com.brainpix.security.dto.request.SignInRequest;

public class SignInConverter {
	public static BrainpixAuthenticationToken toAuthenticationToken(SignInRequest signInRequest) {
		return new BrainpixAuthenticationToken(signInRequest.getId(), signInRequest.getPassword());
	}
}
