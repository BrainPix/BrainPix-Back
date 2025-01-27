package com.brainpix.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CreateReplyDto {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Request {
		@NotBlank(message = "댓글 내용은 필수입니다.")
		String content;    // 댓글 내용
	}

	@Builder
	@Getter
	public static class Parameter {
		private Long postId;    // 게시글 ID
		private Long commentId;    // 부모 댓글 ID
		private Long senderId;    // 작성자 ID
		private String content;    // 댓글 내용
	}
}
