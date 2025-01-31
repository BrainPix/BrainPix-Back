package com.brainpix.joining.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdeaMarketPurchaseDto {
	private Long purchasingId;
	private LocalDateTime purchasedAt;

	// 게시글 정보
	private String category;      // "아이디어마켓 > IT_TECH"
	private String title;
	private String writerName;
	private String writerType;    // "개인"/"회사"

	// 수량
	private Long quantity;

	// 결제금액 계산
	private Long fee;            // 수수료
	private Long finalPrice;     // (price × quantity) + fee
	
}

