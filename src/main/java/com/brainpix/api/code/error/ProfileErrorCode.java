package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE_USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
	PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE_NOT_FOUND", "존재하지 않는 프로필입니다");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}