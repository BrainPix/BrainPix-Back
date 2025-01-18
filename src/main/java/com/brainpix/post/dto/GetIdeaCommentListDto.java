package com.brainpix.post.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class GetIdeaCommentListDto {

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

	@Getter
	@Builder
	public static class ResponseData {
		private Long commentId;           // 댓글 ID
		private Long writerId;              // 댓글 작성자 식별자 값
		private String content;           // 댓글 내용
		private String writerName;            // 댓글 작성자 이름
		private Long parentCommentId;        // 부모 댓글 ID
		private LocalDate createdDate;       // 댓글 작성일
		private List<ResponseData> childComments;      // Q&A 목록
	}
}
