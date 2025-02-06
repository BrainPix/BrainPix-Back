package com.brainpix.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EmailAuthCode {

	@Getter
	@NoArgsConstructor
	public static class Request {
		private String email;
		private String authCode;
	}

	@Getter
	@Builder
	public static class Response {
		private String token;
	}

}
