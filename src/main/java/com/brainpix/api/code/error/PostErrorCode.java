package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

	// 403 Forbidden - 권한 없음
	FORBIDDEN_ACCESS(HttpStatus.UNAUTHORIZED, "POST403", "해당 요청에 대한 권한이 없습니다."),

	// 404 Not Found - 리소스를 찾을 수 없음
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404", "게시글을 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404", "사용자를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
