package com.brainpix.kakaopay.converter;

import com.brainpix.kakaopay.dto.KakaoPayReadyDto;

public class KakaoPayReadyDtoConverter {

	public static KakaoPayReadyDto.Parameter toParameter(Long userId, KakaoPayReadyDto.Request request) {

		return KakaoPayReadyDto.Parameter.builder()
			.buyerId(userId)
			.sellerId(request.getSellerId())
			.ideaId(request.getIdeaId())
			.quantity(request.getQuantity())
			.totalPrice(request.getTotalPrice())
			.vat(request.getVat())
			.build();
	}

	public static KakaoPayReadyDto.Response toResponse(KakaoPayReadyDto.KakaoApiResponse kakaoApiResponse,
		String orderId) {

		return KakaoPayReadyDto.Response.builder()
			.nextRedirectPcUrl(kakaoApiResponse.getNext_redirect_pc_url())
			.orderId(orderId)
			.build();
	}
}
