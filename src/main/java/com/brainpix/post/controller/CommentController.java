package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.converter.CreateCommentDtoConverter;
import com.brainpix.post.converter.CreateReplyDtoConverter;
import com.brainpix.post.converter.GetCommentListDtoConverter;
import com.brainpix.post.dto.CreateCommentDto;
import com.brainpix.post.dto.CreateReplyDto;
import com.brainpix.post.dto.GetCommentListDto;
import com.brainpix.post.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping
	public ResponseEntity<ApiResponse<GetCommentListDto.Response>> getCommentList(
		@PathVariable("postId") Long postId,
		Pageable pageable
	) {
		GetCommentListDto.Parameter parameter = GetCommentListDtoConverter.toParameter(postId, pageable);
		GetCommentListDto.Response response = commentService.getCommentList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	// 부모 댓글 (userId : 작성자)
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createComment(@PathVariable("postId") Long postId,
		@RequestParam("userId") Long userId,
		@Valid CreateCommentDto.Request request) {
		CreateCommentDto.Parameter parameter = CreateCommentDtoConverter.toParameter(postId, userId, request);
		commentService.createComment(parameter);
		return ResponseEntity.ok(ApiResponse.createdWithNoData());
	}

	// 대댓글 (userId : 작성자)
	@PostMapping("/reply")
	public ResponseEntity<ApiResponse<Void>> createReply(@PathVariable("postId") Long postId,
		@RequestParam("userId") Long userId,
		@Valid CreateReplyDto.Request request) {
		CreateReplyDto.Parameter parameter = CreateReplyDtoConverter.toParameter(postId, userId, request);
		commentService.createReply(parameter);
		return ResponseEntity.ok(ApiResponse.createdWithNoData());
	}
}
