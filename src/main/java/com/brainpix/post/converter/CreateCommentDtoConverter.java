package com.brainpix.post.converter;

import com.brainpix.post.dto.CreateCommentDto;
import com.brainpix.post.entity.Comment;
import com.brainpix.post.entity.Post;
import com.brainpix.user.entity.User;

public class CreateCommentDtoConverter {

	public static CreateCommentDto.Parameter toParameter(Long postId, Long userId, CreateCommentDto.Request request) {
		return CreateCommentDto.Parameter.builder()
			.postId(postId)
			.senderId(userId)
			.content(request.getContent())
			.build();
	}

	public static Comment toComment(User writer, Post post, String content) {
		return Comment.builder()
			.writer(writer)
			.parentPost(post)
			.content(content)
			.build();
	}

	public static CreateCommentDto.Response toResponse(Long commentId) {
		return CreateCommentDto.Response.builder()
			.commentId(commentId)
			.build();
	}
}
