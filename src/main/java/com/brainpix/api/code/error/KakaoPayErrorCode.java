package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KakaoPayErrorCode implements ErrorCode {

	// 400 Bad Request - 잘못된 요청
	IDEA_OWNER_PURCHASE_INVALID(HttpStatus.BAD_REQUEST, "KAKAOPAY400", "글 작성자는 구매할 수 없습니다."),
	QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "KAKAOPAY400", "구매 수량은 재고를 초과할 수 없습니다."),

	// 500 Internal Server Error - 서버 내부 오류
	API_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "KAKAOPAY500", "카카오페이 API 호출 중 오류가 발생하였습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
