package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailErrorCode implements ErrorCode {
	MAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL500", "메일 전송에 실패했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
