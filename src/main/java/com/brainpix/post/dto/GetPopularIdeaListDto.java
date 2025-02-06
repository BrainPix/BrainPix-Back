package com.brainpix.post.dto;

import org.springframework.data.domain.Pageable;

import com.brainpix.post.entity.idea_market.IdeaMarketType;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetPopularIdeaListDto {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Request {
		@NotBlank(message = "아이디어 타입은 필수입니다. (IDEA_SOLUTION, MARKET_PLACE)")
		private String type;    // 아이디어 타입 (IDEA_SOLUTION, MARKET_PLACE)
	}

	@Builder
	@Getter
	public static class Parameter {
		private IdeaMarketType type;    // 아이디어 타입 (IDEA_SOLUTION, MARKET_PLACE)
		private Pageable pageable;    // 페이징 기준
	}

	@Builder
	@Getter
	public static class IdeaDetail {
		private Long ideaId;                // 게시글의 식별자 값
		private String auth;                    // 공개 범위
		private String writerImageUrl;            // 작성자 프로필
		private String writerName;                // 작성자 이름
		private String thumbnailImageUrl;          // 대표 이미지 경로
		private String title;                // 제목
		private Long price;                // 가격
		private String category;    // 게시글의 카테고리
		private Long saveCount;        // 저장 횟수
		private Long viewCount;        // 조회수
	}
}
