package com.brainpix.kakaopay.converter;

import com.brainpix.kakaopay.dto.KakaoPaymentDataDto;

public class KakaoPaymentDataDtoConverter {

	public static KakaoPaymentDataDto toKakaoPaymentDataDto(String tid, Long buyerId, Long quantity) {

		return KakaoPaymentDataDto.builder()
			.tid(tid)
			.buyerId(buyerId)
			.quantity(quantity)
			.build();
	}
}
