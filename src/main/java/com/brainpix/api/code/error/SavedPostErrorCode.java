package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SavedPostErrorCode implements ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "SAVED_POST_USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "SAVED_POST_POST_NOT_FOUND", "존재하지 않는 게시물입니다."),
	DUPLICATE_SAVED_POST(HttpStatus.CONFLICT, "SAVED_POST_DUPLICATE", "이미 저장된 게시물입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}