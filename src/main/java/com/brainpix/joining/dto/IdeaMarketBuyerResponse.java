package com.brainpix.joining.dto;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.entity.quantity.PaymentDuration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdeaMarketBuyerResponse {
	private Long buyerId;
	private String buyerName;
	private PaymentDuration paymentDuration;

	public static IdeaMarketBuyerResponse from(IdeaMarketPurchasing purchasing) {
		return IdeaMarketBuyerResponse.builder()
			.buyerId(purchasing.getBuyer().getId())
			.buyerName(purchasing.getBuyer().getName())
			.paymentDuration(purchasing.getPaymentDuration())
			.build();
	}
}

