package com.brainpix.kakaopay.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoPaymentDataDto {

	private String tid;    // 카카오페이 쪽 주문 고유 번호
	private Long buyerId;    // 유저 ID
	private Long quantity;    // 구매 수량
}
