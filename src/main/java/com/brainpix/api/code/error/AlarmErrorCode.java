package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmErrorCode implements ErrorCode {
	ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "ALARM404", "알림을 찾을 수 없습니다."),

	ALARM_ALREADY_READ(HttpStatus.BAD_REQUEST, "ALARM400", "이미 읽은 알림입니다."),
	NOT_ALARM_RECEIVER(HttpStatus.BAD_REQUEST, "ALARM400", "알림 수신자가 아닙니다."),

	ALARM_ALREADY_TRASHED(HttpStatus.BAD_REQUEST, "ALARM400", "이미 휴지통에 있는 알림입니다."),
	ALARM_NOT_TRASHED(HttpStatus.BAD_REQUEST, "ALARM400", "휴지통에 없는 알림입니다.");
	
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
