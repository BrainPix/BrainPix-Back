package com.brainpix.post.dto;

import lombok.Builder;
import lombok.Getter;

public class DeleteCommentDto {

	@Builder
	@Getter
	public static class Parameter {
		private Long postId;    // 게시글 ID
		private Long commentId;    // 삭제할 댓글 ID
		private Long writerId;    // 작성자 ID
	}
}
