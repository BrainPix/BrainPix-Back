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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

	private final SavedPostService savedPostService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> savePost(@RequestParam long userId, @RequestParam long postId) {
		savedPostService.savePost(userId, postId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping("/request-tasks")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostRequestTaskResponse>>> getSavedRequestTasks(
		@RequestParam long userId, Pageable pageable) {
		Page<SavedPostRequestTaskResponse> result = savedPostService.findSavedRequestTasks(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@GetMapping("/idea-markets")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostIdeaMarketResponse>>> getSavedIdeaMarkets(
		@RequestParam long userId, Pageable pageable) {
		Page<SavedPostIdeaMarketResponse> result = savedPostService.findSavedIdeaMarkets(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@GetMapping("/collaboration-hubs")
	public ResponseEntity<ApiResponse<CommonPageResponse<SavedPostCollaborationResponse>>> getSavedCollaborationHubs(
		@RequestParam long userId, Pageable pageable) {
		Page<SavedPostCollaborationResponse> result = savedPostService.findSavedCollaborationHubs(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

}