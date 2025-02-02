package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityErrorCode implements ErrorCode {
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTHORITY400", "비밀번호가 일치하지 않습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
