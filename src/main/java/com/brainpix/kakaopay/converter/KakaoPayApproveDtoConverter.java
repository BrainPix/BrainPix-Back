package com.brainpix.kakaopay.converter;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.kakaopay.dto.KakaoPayApproveDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.User;

public class KakaoPayApproveDtoConverter {

	public static KakaoPayApproveDto.Parameter toParameter(Long userId, KakaoPayApproveDto.Request request) {

		return KakaoPayApproveDto.Parameter.builder()
			.userId(userId)
			.orderId(request.getOrderId())
			.pgToken(request.getPgToken())
			.build();
	}

	public static IdeaMarketPurchasing toIdeaMarketPurchasing(KakaoPayApproveDto.KakaoApiResponse kakaoApiResponse,
		User user,
		IdeaMarket ideaMarket, PaymentDuration paymentDuration) {

		return IdeaMarketPurchasing.builder()
			.buyer(user)
			.ideaMarket(ideaMarket)
			.paymentDuration(paymentDuration)
			.price(kakaoApiResponse.getAmount().getTotal())
			.vat(kakaoApiResponse.getAmount().getVat())
			.quantity(kakaoApiResponse.getQuantity())
			.paymentDuration(paymentDuration)
			.build();
	}

	public static KakaoPayApproveDto.Response toResponse(IdeaMarketPurchasing ideaMarketPurchasing) {

		return KakaoPayApproveDto.Response.builder()
			.ideaMarketPurchasingId(ideaMarketPurchasing.getId())
			.build();
	}
}
