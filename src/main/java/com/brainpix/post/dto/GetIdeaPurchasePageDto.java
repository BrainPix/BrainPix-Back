package com.brainpix.post.dto;

import lombok.Builder;
import lombok.Getter;

public class GetIdeaPurchasePageDto {

	@Builder
	@Getter
	public static class Parameter {
		private Long ideaId;    // 아이디어 ID
		private Long userId;    // 유저 ID
	}

	@Builder
	@Getter
	public static class Response {
		private Long ideaId;    // 아이디어 ID
		private String thumbnailImageUrl;   // 썸네일 이미지 URL
		private Long price;                 // 아이디어 가격
		private String title;               // 아이디어 제목
		private Long remainingQuantity;     // 남은 수량
		private Long sellerId;            // 판매자의 식별자 값
		private String name;              // 판매자 이름
		private String profileImageUrl;   // 판매자 프로필 이미지 URL
		private String email;    // 판매자 이메일
	}
}
