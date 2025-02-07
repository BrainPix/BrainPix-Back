package com.brainpix.kakaopay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoPayApproveDto {

	@NoArgsConstructor
	@Getter
	public static class Request {
		@NotBlank(message = "토큰을 입력해주세요.")
		private String pgToken;    // 결제 최종 완료를 위한 토큰
		@NotBlank(message = "주문 번호를 입력해주세요.")
		private String orderId;    // 주문 번호 (세션에서 결제 정보 탐색 Key)
		@NotNull(message = "아이디어 게시글 ID를 입력해주세요.")
		private Long ideaId;        // 아이디어 ID
	}

	@Getter
	@Builder
	public static class Parameter {
		private Long userId;    // 유저 ID
		private String orderId;    // 주문 번호 (결제 정보 탐색 Key)
		private String pgToken;    // 결제 최종 완료를 위한 토큰
		private Long ideaId;    // 아이디어 ID
	}

	@Getter
	@Builder
	public static class KakaoApiResponse {
		private String aid;    // 요청 고유 번호
		private String tid;    // 결제 고유 번호
		private String cid;    // 가맹점 코드
		private String partner_order_id;    // 주문 번호 (DB에 저장)
		private String partner_user_id;    // 구매자 ID
		private String item_name;    // 상품 이름
		private Long quantity;    // 상품 수량
		private Amount amount;    // 결제 금액 관련
		private String approved_at;    // 결제 승인 시각
	}

	@Getter
	@Builder
	public static class Amount {
		private Long total;    // 총 결제 금액
		private Long vat;    // 부가세 금액
	}

	@Getter
	@Builder
	public static class Response {
		private Long ideaMarketPurchasingId;
	}
}
