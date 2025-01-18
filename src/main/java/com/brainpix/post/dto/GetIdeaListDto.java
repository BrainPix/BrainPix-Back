package com.brainpix.post.dto;

import java.util.List;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetIdeaListDto {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Request {
		private IdeaMarketType type;    // 아이디어 타입
		private String keyword;        // 검색 키워드
		private Specialization category;  // 카테고리
		private Boolean onlyCompany;  // 기업 공개 제외/기업 공개만 보기
		private SortType sortType;    // 정렬 기준
	}

	@Builder
	@Getter
	public static class Response {
		List<ResponseData> responseDataList;    // 결과 값 리스트
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
		private PostAuth auth;        // 공개 범위 (ALL, COMPANY)
		private String writerImageUrl;  // 작성자 프로필 이미지 경로
		private String writerName;      // 작성자 닉네임
		private String thumbnailImageUrl;          // 대표 이미지 경로
		private String title;               // 아이디어 제목
		private Long price;                 // 가격
		private Specialization specialization;  // 분야
		private Long saveCount;		// 저장수
		private Long viewCount;		// 조회수
	}
}
