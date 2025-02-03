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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

	private final SavedPostService savedPostService;

	@AllUser
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> savePost(@UserId Long userId, @RequestParam long postId) {
		savedPostService.savePost(userId, postId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@GetMapping("/request-tasks")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostRequestTaskResponse>>> getSavedRequestTasks(
		@UserId Long userId, Pageable pageable) {
		Page<SavedPostRequestTaskResponse> result = savedPostService.findSavedRequestTasks(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@AllUser
	@GetMapping("/idea-markets")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostIdeaMarketResponse>>> getSavedIdeaMarkets(
		@UserId Long userId, Pageable pageable) {
		Page<SavedPostIdeaMarketResponse> result = savedPostService.findSavedIdeaMarkets(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@AllUser
	@GetMapping("/collaboration-hubs")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostCollaborationResponse>>> getSavedCollaborationHubs(
		@UserId Long userId, Pageable pageable) {
		Page<SavedPostCollaborationResponse> result = savedPostService.findSavedCollaborationHubs(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

}