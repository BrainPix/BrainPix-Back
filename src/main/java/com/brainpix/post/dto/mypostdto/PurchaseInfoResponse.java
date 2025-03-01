package com.brainpix.post.dto.mypostdto;

import com.brainpix.joining.entity.purchasing.Payment;

public record PurchaseInfoResponse(
	String buyerID,      // 구매자 아이디
	Long userId,
	Payment payment, // 거래 방식 (예: 신용카드, 계좌이체 등)
	Long totalPay      // 지불 금액
) {
}
