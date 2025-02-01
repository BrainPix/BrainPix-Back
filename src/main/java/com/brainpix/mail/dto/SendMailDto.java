package com.brainpix.mail.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class SendMailDto {
	private final String receiverEmail;
	private final String signupCode;
	private final String title;
}
