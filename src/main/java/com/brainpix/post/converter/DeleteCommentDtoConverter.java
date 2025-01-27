package com.brainpix.post.converter;

import com.brainpix.post.dto.DeleteCommentDto;

public class DeleteCommentDtoConverter {

	public static DeleteCommentDto.Parameter toParameter(Long postId, Long commentId, Long userId) {
		return DeleteCommentDto.Parameter.builder()
			.postId(postId)
			.commentId(commentId)
			.writerId(userId)
			.build();
	}
}
