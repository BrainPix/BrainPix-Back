package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityErrorCode implements ErrorCode {
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTHORITY400", "비밀번호가 일치하지 않습니다."),
	AUTHORITY_ERROR_CODE(HttpStatus.FORBIDDEN, "AUTHORITY403", "권한이 없습니다."),
	EMAIL_AUTH_CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUTHORITY400", "인증 코드를 먼저 요청해주세요."),
	EMAIL_AUTH_CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTHORITY400", "인증 코드가 일치하지 않습니다."),
	EMAIL_AUTH_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "AUTHORITY400", "인증 코드가 만료되었습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
