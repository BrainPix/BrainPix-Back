package com.brainpix.post.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;

public class GetCommentListDto {

	@Builder
	@Getter
	public static class Parameter {
		private Long userId;    // 유저 ID
		private Long postId;    // 게시글 ID
		private Pageable pageable;    // 페이징 기준
	}

	@Getter
	@Builder
	public static class Comment {
		private Long commentId;           // 댓글 ID
		private Long writerId;              // 댓글 작성자 식별자 값
		private String content;           // 댓글 내용
		private String writerName;            // 댓글 작성자 이름
		private String profileImageUrl; // 댓글 작성자의 프로필 이미지
		private LocalDate createdDate;       // 댓글 작성일
		private Boolean isMyComment;    // 내 댓글인지 여부
		private Long parentCommentId;        // 부모 댓글 ID
		private List<Comment> childComments;      // Q&A 목록
	}
}
