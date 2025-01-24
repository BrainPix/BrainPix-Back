package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
	IDENTIFIER_DUPLICATED(HttpStatus.BAD_REQUEST, "SECURITY400", "이미 존재하는 아이디입니다."),
	NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "SECURITY400", "이미 존재하는 닉네임입니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
