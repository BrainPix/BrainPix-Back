package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CollectionErrorCode implements ErrorCode {
	COLLECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLECTION404", "지원 내역이 존재하지 않습니다."),
	NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "COLLECTION403", "본인이 지원한 항목이 아닙니다."),
	INVALID_STATUS(HttpStatus.BAD_REQUEST, "COLLECTION400", "거절 상태가 아닌 항목은 삭제할 수 없습니다."),

	RECRUITMENT_ALREADY_FULL(HttpStatus.BAD_REQUEST, "COLLECTION400", "신청 가능한 자리가 없습니다."),
	RECRUITMENT_ALREADY_APPLY(HttpStatus.BAD_REQUEST, "COLLECTION400", "이미 신청한 분야입니다."),
	RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLECTION404", "존재하지 않는 모집 분야입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

}
