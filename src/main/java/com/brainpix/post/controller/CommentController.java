package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.swagger.SwaggerPageable;
import com.brainpix.post.converter.CreateCommentDtoConverter;
import com.brainpix.post.converter.CreateReplyDtoConverter;
import com.brainpix.post.converter.DeleteCommentDtoConverter;
import com.brainpix.post.converter.GetCommentListDtoConverter;
import com.brainpix.post.dto.CreateCommentDto;
import com.brainpix.post.dto.CreateReplyDto;
import com.brainpix.post.dto.DeleteCommentDto;
import com.brainpix.post.dto.GetCommentListDto;
import com.brainpix.post.service.CommentService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
@Tag(name = "게시글 댓글 목록 조회/생성/삭제 API", description = "게시글 상세 조회 시 보이는 댓글 목록을 조회/생성/삭제 하는 API입니다.")
public class CommentController {

	private final CommentService commentService;

	@SwaggerPageable
	@Operation(summary = "댓글 목록 조회 API", description = "경로변수로 postId를 입력받아 해당 게시글의 댓글 목록을 조회합니다.<br>페이징을 위한 page와 size는 쿼리 파라미터로 입력받습니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<GetCommentListDto.Comment>>> getCommentList(
		@UserId Long userId,
		@PathVariable("postId") Long postId,
		@PageableDefault(page = 0, size = 10) Pageable pageable
	) {
		GetCommentListDto.Parameter parameter = GetCommentListDtoConverter.toParameter(userId, postId, pageable);
		CommonPageResponse<GetCommentListDto.Comment> response = commentService.getCommentList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	// 부모 댓글 (userId : 작성자)
	@AllUser
	@Operation(summary = "댓글 등록 API", description = "경로변수로 postId를 입력받아 해당 게시글에 댓글을 등록합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<CreateCommentDto.Response>> createComment(@PathVariable("postId") Long postId,
		@UserId Long userId,
		@Valid @RequestBody CreateCommentDto.Request request) {
		CreateCommentDto.Parameter parameter = CreateCommentDtoConverter.toParameter(postId, userId, request);
		CreateCommentDto.Response response = commentService.createComment(parameter);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	// 대댓글 (userId : 작성자)
	@AllUser
	@Operation(summary = "대댓글 등록 API", description = "경로변수로 postId와 부모 댓글인 commentId를 입력받아 대댓글을 등록합니다.")
	@PostMapping("/{commentId}/reply")
	public ResponseEntity<ApiResponse<CreateReplyDto.Response>> createReply(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId,
		@UserId Long userId,
		@Valid @RequestBody CreateReplyDto.Request request) {
		CreateReplyDto.Parameter parameter = CreateReplyDtoConverter.toParameter(postId, commentId, userId, request);
		CreateReplyDto.Response response = commentService.createReply(parameter);
		return ResponseEntity.ok(ApiResponse.created(response));
	}

	// 댓글 삭제
	@AllUser
	@Operation(summary = "댓글 삭제 API", description = "경로변수로 postId와 댓글 식별자인 commentId를 입력받아 댓글을 삭제합니다. 작성자만 삭제 가능합니다.")
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId,
		@UserId Long userId) {
		DeleteCommentDto.Parameter parameter = DeleteCommentDtoConverter.toParameter(postId, commentId, userId);
		commentService.deleteComment(parameter);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
