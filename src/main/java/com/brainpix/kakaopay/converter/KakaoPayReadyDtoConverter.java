package com.brainpix.kakaopay.converter;

import com.brainpix.kakaopay.dto.KakaoPayReadyDto;
import com.brainpix.kakaopay.entity.KakaoPaymentData;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.User;

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

	public static KakaoPaymentData toKakaoPaymentData(KakaoPayReadyDto.KakaoApiResponse kakaoApiResponse,
		KakaoPayReadyDto.Parameter parameter,
		String orderId, User buyer,
		IdeaMarket ideaMarket) {

		return KakaoPaymentData.builder()
			.tid(kakaoApiResponse.getTid())
			.quantity(parameter.getQuantity())
			.orderId(orderId)
			.buyer(buyer)
			.ideaMarket(ideaMarket)
			.build();
	}
}
