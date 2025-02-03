package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageErrorCode implements ErrorCode {

	MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "MESSAGE404", "찾을 수 없는 메시지입니다."),
	SENDER_NOT_FOUND(HttpStatus.NOT_FOUND, "SENDER404", "송신자를 찾을 수 없습니다."),
	RECEIVER_NOT_FOUND(HttpStatus.NOT_FOUND, "RECEIVER404", "수신자를 찾을 수 없습니다."),

	NOT_YOUR_MESSAGE(HttpStatus.FORBIDDEN, "NOT_YOUR_MESSAGE", "본인의 메시지가 아닙니다."),
	INVALID_SEARCH_TYPE(HttpStatus.BAD_REQUEST, "INVALID_SEARCH_TYPE", "유효하지 않은 검색 조건입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
