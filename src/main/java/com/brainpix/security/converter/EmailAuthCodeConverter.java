package com.brainpix.security.converter;

import com.brainpix.security.dto.EmailAuthCode;

public class EmailAuthCodeConverter {
	public static EmailAuthCode.Response toResponse(String token) {
		return EmailAuthCode.Response.builder()
			.token(token)
			.build();
	}
}
