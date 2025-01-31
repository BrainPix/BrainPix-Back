package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitmentErrorCode implements ErrorCode{

	INVALID_INPUT(HttpStatus.BAD_REQUEST, "RECRUITMENT400" ,"모집 정보가 필요합니다."),
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "RECRUITMENT400" ,"도메인과 가격 정보를 정확히 입력해야 합니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
