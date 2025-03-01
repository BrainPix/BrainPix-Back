package com.brainpix.post.converter;

import com.brainpix.post.dto.CreateReplyDto;
import com.brainpix.post.entity.Comment;
import com.brainpix.post.entity.Post;
import com.brainpix.user.entity.User;

public class CreateReplyDtoConverter {

	public static CreateReplyDto.Parameter toParameter(Long postId, Long commentId, Long userId,
		CreateReplyDto.Request request) {
		return CreateReplyDto.Parameter.builder()
			.postId(postId)
			.commentId(commentId)
			.senderId(userId)
			.content(request.getContent())
			.build();
	}

	public static Comment toComment(User writer, Post post, Comment comment, String content) {
		return Comment.builder()
			.writer(writer)
			.parentPost(post)
			.parentComment(comment)
			.content(content)
			.build();
	}

	public static CreateReplyDto.Response toResponse(Long commentId) {
		return CreateReplyDto.Response.builder()
			.commentId(commentId)
			.build();
	}
}
