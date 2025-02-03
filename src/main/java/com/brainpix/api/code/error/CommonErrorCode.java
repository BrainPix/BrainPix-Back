package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

	// COMMON 4XX
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON400", "파라미터가 올바르지 않습니다."),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "찾을 수 없는 리소스입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "사용자를 찾을 수 없습니다"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON405", "허용되지 않는 HTTP Method입니다."),

	// COMMON 5XX
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 오류입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
