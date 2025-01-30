package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE_USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
	INVALID_USER_TYPE(HttpStatus.NOT_FOUND, "PROFILE_USER_NOT_FOUND", "올바르지 않은 유저타입입니다."),
	PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE_NOT_FOUND", "존재하지 않는 프로필입니다."),
	INVALID_PROFILE_TYPE(HttpStatus.BAD_REQUEST, "INVALID_PROFILE_TYPE", "잘못된 프로필 유형입니다."),
	NOT_OWNED_PORTFOLIO(HttpStatus.FORBIDDEN, "NOT_OWNED_PORTFOLIO", "소유하지 않은 포트폴리오입니다."),
	INVALID_SPECIALIZATION(HttpStatus.BAD_REQUEST, "INVALID_SPECIALIZATION", "유효하지 않은 전문 분야입니다."),
	FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_UPLOAD_ERROR", "파일 업로드에 실패했습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "내부 서버 오류가 발생했습니다."),
	DUPLICATE_SPECIALIZATION(HttpStatus.BAD_REQUEST, "DUPLICATE_SPECIALIZATION", "중복된 전문 분야는 선택할 수 없습니다."),
	MAX_SPECIALIZATIONS_EXCEEDED(HttpStatus.BAD_REQUEST, "MAX_SPECIALIZATIONS_EXCEEDED", "최대 2개의 전문 분야만 선택할 수 있습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}