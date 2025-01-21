package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode{

	// 403 Forbidden - 권한 없음
	FORBIDDEN_ACCESS(HttpStatus.UNAUTHORIZED,"POST403", "해당 요청에 대한 권한이 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
