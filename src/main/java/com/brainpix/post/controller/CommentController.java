package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.converter.CreateCommentDtoConverter;
import com.brainpix.post.converter.CreateReplyDtoConverter;
import com.brainpix.post.converter.DeleteCommentDtoConverter;
import com.brainpix.post.converter.GetCommentListDtoConverter;
import com.brainpix.post.dto.CreateCommentDto;
import com.brainpix.post.dto.CreateReplyDto;
import com.brainpix.post.dto.DeleteCommentDto;
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
	public ResponseEntity<ApiResponse<CommonPageResponse<GetCommentListDto.Comment>>> getCommentList(
		@PathVariable("postId") Long postId,
		Pageable pageable
	) {
		GetCommentListDto.Parameter parameter = GetCommentListDtoConverter.toParameter(postId, pageable);
		CommonPageResponse<GetCommentListDto.Comment> response = commentService.getCommentList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	// 부모 댓글 (userId : 작성자)
	@PostMapping
	public ResponseEntity<ApiResponse<CreateCommentDto.Response>> createComment(@PathVariable("postId") Long postId,
		@RequestParam("userId") Long userId,
		@Valid @RequestBody CreateCommentDto.Request request) {
		CreateCommentDto.Parameter parameter = CreateCommentDtoConverter.toParameter(postId, userId, request);
		CreateCommentDto.Response response = commentService.createComment(parameter);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	// 대댓글 (userId : 작성자)
	@PostMapping("/{commentId}/reply")
	public ResponseEntity<ApiResponse<CreateReplyDto.Response>> createReply(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId,
		@RequestParam("userId") Long userId,
		@Valid @RequestBody CreateReplyDto.Request request) {
		CreateReplyDto.Parameter parameter = CreateReplyDtoConverter.toParameter(postId, commentId, userId, request);
		CreateReplyDto.Response response = commentService.createReply(parameter);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId,
		@RequestParam("userId") Long userId) {
		DeleteCommentDto.Parameter parameter = DeleteCommentDtoConverter.toParameter(postId, commentId, userId);
		commentService.deleteComment(parameter);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
