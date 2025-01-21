package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriceErrorCode implements ErrorCode{
	INVALID_QUANTITY_INPUT(HttpStatus.BAD_REQUEST ,"PRICE400", "현재 인원이 모집 인원보다 많아 오류가 발생했습니다."),
	INVALID_RECRUITMENT_INPUT(HttpStatus.BAD_REQUEST,"PRICE400", "가격 정보가 잘못되었습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
