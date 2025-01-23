package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioErrorCode implements ErrorCode {

	// Portfolio 관련 에러
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "PORTFOLIO404", "사용자를 찾을 수 없습니다."),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "PORTFOLIO404", "리소스를 찾을 수 없습니다."),
	PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "PORTFOLIO404", "포트폴리오를 찾을 수 없습니다."),
	NOT_OWNED_PORTFOLIO(HttpStatus.FORBIDDEN, "PORTFOLIO403", "본인 소유의 포트폴리오가 아닙니다."),
	INVALID_PORTFOLIO_PARAMETER(HttpStatus.BAD_REQUEST, "PORTFOLIO400", "포트폴리오 요청 데이터가 올바르지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}