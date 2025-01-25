package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.converter.GetCommentListDtoConverter;
import com.brainpix.post.dto.GetCommentListDto;
import com.brainpix.post.entity.Comment;
import com.brainpix.post.entity.Post;
import com.brainpix.post.repository.CommentRepository;
import com.brainpix.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public GetCommentListDto.Response getCommentList(GetCommentListDto.Parameter parameter) {

		// 게시글 조회
		Post post = postRepository.findById(parameter.getPostId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 게시글에 연관된 모든 댓글을 조회
		Page<Comment> comments = commentRepository.findByParentPostId(post.getId(), parameter.getPageable());

		return GetCommentListDtoConverter.toResponse(comments);
	}
}
