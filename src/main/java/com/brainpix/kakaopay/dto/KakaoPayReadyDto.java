package com.brainpix.kakaopay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoPayReadyDto {

	@NoArgsConstructor
	@Getter
	public static class Request {
		@NotNull(message = "아이디어 ID가 필요합니다.")
		private Long ideaId;        // 아이디어 ID
		@NotNull(message = "판매자 ID가 필요합니다.")
		private Long sellerId;        // 판매자 ID
		@NotNull(message = "상품 수량 필수입니다.")
		private Long quantity;        // 상품 수량
		@NotNull(message = "총 결제 금액은 필수입니다.")
		private Long totalPrice;     // 총 결제금액
		@NotNull(message = "VAT는 필수입니다.")
		private Long vat;        // VAT
	}

	@Getter
	@Builder
	public static class Parameter {
		private Long buyerId;    // 구매자 ID
		private Long ideaId;        // 아이디어 ID
		private Long sellerId;        // 판매자 ID
		private Long quantity;        // 상품 수량
		private Long totalPrice;     // 총 결제금액
		private Long vat;        // VAT
	}

	@Getter
	@Builder
	public static class KakaoApiResponse {
		private String tid;        // 카카오페이 쪽 결제 고유 번호
		private String next_redirect_pc_url;    // 카카오페이가 생성한 결제 경로, 사용자는 해당 경로로 이동하여 결제 진행
	}

	@Getter
	@Builder
	public static class Response {
		private String tid;
		private String nextRedirectPcUrl;
		private String orderId;    // 최종 승인(approve)때, 세션에서 결제 정보를 가져오는데 Key로 사용하기 위함
	}
}
