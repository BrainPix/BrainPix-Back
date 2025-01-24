package com.brainpix.post.dto;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetCollaborationHubListDto {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Request {
		private String keyword;        // 검색 키워드
		private String category;  // 카테고리
		private Boolean onlyCompany;  // 기업 공개 제외/기업 공개만 보기
		private String sortType;    // 정렬 기준
	}

	@Builder
	@Getter
	public static class Parameter {
		private String keyword;        // 검색 키워드
		private Specialization category;  // 카테고리
		private Boolean onlyCompany;  // 기업 공개 제외/기업 공개만 보기
		private SortType sortType;    // 정렬 기준
		private Pageable pageable;    // 페이징 기준
	}

	@Builder
	@Getter
	public static class Response {
		private List<CollaborationDetail> collaborationDetailList;    // 결과 값 리스트
		private Integer totalPages;        // 전체 페이지 수
		private Integer totalElements;    // 전체 결과의 크기
		private Integer currentPage;    // 현재 페이지 수
		private Integer currentSize;    // 현재 페이지의 크기
		private Boolean hasNext;    // 다음 페이지 존재 여부
	}

	@Builder
	@Getter
	public static class CollaborationDetail {
		private Long collaborationId;                // 게시글의 식별자 값
		private String auth;        // 공개 범위 (ALL, COMPANY)
		private String writerImageUrl;  // 작성자 프로필 이미지 경로
		private String writerName;      // 작성자 닉네임
		private String thumbnailImageUrl;          // 대표 이미지 경로
		private String title;               // 아이디어 제목
		private Long deadline;                 // 남은 기간
		private String category;  // 게시글의 카테고리
		private Long occupiedQuantity;  // 현재 인원
		private Long totalQuantity;     // 전체 인원
		private Long saveCount;        // 저장수
		private Long viewCount;        // 조회수
	}
}
