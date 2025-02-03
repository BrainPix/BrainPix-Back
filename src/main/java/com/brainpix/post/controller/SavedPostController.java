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
import com.brainpix.post.dto.SavedPostCollaborationResponse;
import com.brainpix.post.dto.SavedPostIdeaMarketResponse;
import com.brainpix.post.dto.SavedPostRequestTaskResponse;
import com.brainpix.post.service.SavedPostService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

	private final SavedPostService savedPostService;

	@Operation(summary = "게시글 저장", description = "현재 로그인한 사용자가 특정 게시글을 저장합니다.")
	@AllUser
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> savePost(@UserId Long userId, @RequestParam long postId) {
		savedPostService.savePost(userId, postId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "저장된 요청 과제 조회", description = "현재 로그인한 사용자가 저장한 요청 과제를 조회합니다.")
	@AllUser
	@GetMapping("/request-tasks")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostRequestTaskResponse>>> getSavedRequestTasks(
		@UserId Long userId, Pageable pageable) {
		Page<SavedPostRequestTaskResponse> result = savedPostService.findSavedRequestTasks(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@Operation(summary = "저장된 아이디어 마켓 조회", description = "현재 로그인한 사용자가 저장한 아이디어 마켓을 조회합니다.")
	@AllUser
	@GetMapping("/idea-markets")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostIdeaMarketResponse>>> getSavedIdeaMarkets(
		@UserId Long userId, Pageable pageable) {
		Page<SavedPostIdeaMarketResponse> result = savedPostService.findSavedIdeaMarkets(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@Operation(summary = "저장된 협업 광장 조회", description = "현재 로그인한 사용자가 저장한 협업 광장을 조회합니다.")
	@AllUser
	@GetMapping("/collaboration-hubs")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostCollaborationResponse>>> getSavedCollaborationHubs(
		@UserId Long userId, Pageable pageable) {
		Page<SavedPostCollaborationResponse> result = savedPostService.findSavedCollaborationHubs(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

}