package com.brainpix.joining.dto;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTaskBuyerResponse {
	private Long buyerId;     // 지원자 ID
	private String buyerName; // 지원자 이름
	private Boolean accepted; // 수락 여부

	public static RequestTaskBuyerResponse from(RequestTaskPurchasing purchase) {
		return RequestTaskBuyerResponse.builder()
			.buyerId(purchase.getBuyer().getId())
			.buyerName(purchase.getBuyer().getName())
			.accepted(purchase.getAccepted())
			.build();
	}
}

