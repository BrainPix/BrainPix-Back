package com.brainpix.post.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;

public class GetIdeaCommentListDto {

	@Builder
	@Getter
	public static class Parameter {
		private Long ideaId;	// 아이디어 ID
		private Pageable pageable;	// 페이징 기준
	}

	@Builder
	@Getter
	public static class Response {
		private List<Comment> commentList;	// 결과 값 리스트
		private Integer totalPages;        // 전체 페이지 수
		private Integer totalElements;    // 전체 결과의 크기
		private Integer currentPage;    // 현재 페이지 수
		private Integer currentSize;    // 현재 페이지의 크기
		private Boolean hasNext;    // 다음 페이지 존재 여부
	}

	@Getter
	@Builder
	public static class Comment {
		private Long commentId;           // 댓글 ID
		private Long writerId;              // 댓글 작성자 식별자 값
		private String content;           // 댓글 내용
		private String writerName;            // 댓글 작성자 이름
		private Long parentCommentId;        // 부모 댓글 ID
		private LocalDate createdDate;       // 댓글 작성일
		private List<Comment> childComments;      // Q&A 목록
	}
}
