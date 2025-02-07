package com.brainpix.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.dto.PostCollaborationResponse;
import com.brainpix.post.dto.PostIdeaMarketResponse;
import com.brainpix.post.dto.PostRequestTaskResponse;
import com.brainpix.post.dto.SavePostResponseDto;
import com.brainpix.post.service.SavedPostService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/saved-posts")
@RequiredArgsConstructor
@Tag(name = "게시글 저장 관련 API", description = "게시글을 저장 및 해제 하고, 저장된 게시글을 조회하는 API입니다.")
public class SavedPostController {

	private final SavedPostService savedPostService;

	@Operation(summary = "게시글 저장 및 해제", description = "토글 형식으로 현재 로그인한 사용자가 특정 게시글을 저장 또는 해제 합니다.<br>이미 저장된 게시글은 해제되고, 그렇지 않다면 저장합니다.")
	@AllUser
	@PostMapping
	public ResponseEntity<ApiResponse<SavePostResponseDto>> savePost(@UserId Long userId, @RequestParam long postId) {
		SavePostResponseDto result = savedPostService.savePost(userId, postId);
		return ResponseEntity.ok(ApiResponse.success(result));
	}

	@Operation(summary = "저장된 요청 과제 조회", description = "현재 로그인한 사용자가 저장한 요청 과제를 조회합니다.")
	@AllUser
	@GetMapping("/request-tasks")
	public ResponseEntity<ApiResponse<CommonPageResponse<PostRequestTaskResponse>>> getSavedRequestTasks(
		@UserId Long userId, Pageable pageable) {
		Page<PostRequestTaskResponse> result = savedPostService.findSavedRequestTasks(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@Operation(summary = "저장된 아이디어 마켓 조회", description = "현재 로그인한 사용자가 저장한 아이디어 마켓을 조회합니다.")
	@AllUser
	@GetMapping("/idea-markets")
	public ResponseEntity<ApiResponse<CommonPageResponse<PostIdeaMarketResponse>>> getSavedIdeaMarkets(
		@UserId Long userId, Pageable pageable) {
		Page<PostIdeaMarketResponse> result = savedPostService.findSavedIdeaMarkets(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@Operation(summary = "저장된 협업 광장 조회", description = "현재 로그인한 사용자가 저장한 협업 광장을 조회합니다.")
	@AllUser
	@GetMapping("/collaboration-hubs")
	public ResponseEntity<ApiResponse<CommonPageResponse<PostCollaborationResponse>>> getSavedCollaborationHubs(
		@UserId Long userId, Pageable pageable) {
		Page<PostCollaborationResponse> result = savedPostService.findSavedCollaborationHubs(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

}