package com.brainpix.post.dto.ideamarket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdeaMarketPurchasingResponse {
    private String buyerName;       // 구매자 이름
    private String paymentDuration; // 결제 방식
    private Long price;             // 지불 금액
}