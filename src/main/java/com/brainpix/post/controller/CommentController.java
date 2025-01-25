package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.converter.GetCommentListDtoConverter;
import com.brainpix.post.dto.GetCommentListDto;
import com.brainpix.post.service.CommentService;

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
}
