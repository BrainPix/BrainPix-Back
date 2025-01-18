package com.brainpix.post.dto;

import java.util.List;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.profile.entity.Specialization;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetPopularIdeaListDto {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Request {
		private IdeaMarketType IdeaType;    // 아이디어 타입 (IDEA_SOLUTION, MARKET_PLACE)
		private Integer page;		// 페이지 수
		private Integer size;		// 조회 개수
	}

	@Builder
	@Getter
	public static class Response {
		List<ResponseData> responseDataList;	// 결과 값 리스트
		Integer totalPages;        // 전체 페이지 수
		Integer totalElements;    // 전체 결과의 크기
		Integer currentPage;    // 현재 페이지 수
		Integer currentSize;    // 현재 페이지의 크기
		Boolean hasNext;    // 다음 페이지 존재 여부
	}

	@Builder
	@Getter
	public static class ResponseData {
		private Long ideaId;                // 게시글의 식별자 값
		private PostAuth auth;					// 공개 범위
		private String writerImageUrl;			// 작성자 프로필
		private String writerName;				// 작성자 이름
		private String thumbnailImageUrl;          // 대표 이미지 경로
		private String title;				// 제목
		private Long price;				// 가격
		private Specialization specialization;		// 분야
		private Long saveCount;		// 저장 횟수
		private Long viewCount;		// 조회수
	}
}
