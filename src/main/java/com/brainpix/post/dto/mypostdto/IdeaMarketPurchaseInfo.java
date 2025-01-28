package com.brainpix.post.dto.mypostdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdeaMarketPurchaseInfo {
	private String buyerId;          // 구매자 identifier (또는 name)
	private String paymentMethod;    // 결제 방식 (ex: "카카오페이")
	private Long paidAmount;         // 지불 금액
}