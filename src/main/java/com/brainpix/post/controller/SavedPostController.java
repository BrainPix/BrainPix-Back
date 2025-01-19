package com.brainpix.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.SavedPostSimpleResponse;
import com.brainpix.post.service.SavedPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

	private final SavedPostService savedPostService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> savePost(@RequestParam long userId, @RequestParam long postId) {
		savedPostService.savePost(userId, postId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping("/request-tasks")
	public ResponseEntity<ApiResponse<List<SavedPostSimpleResponse>>> getSavedRequestTasks(@RequestParam long userId) {
		List<SavedPostSimpleResponse> result = savedPostService.findSavedRequestTasks(userId);
		return ResponseEntity.ok(ApiResponse.success(result));
	}

	@GetMapping("/idea-markets")
	public ResponseEntity<ApiResponse<List<SavedPostSimpleResponse>>> getSavedIdeaMarkets(@RequestParam long userId) {
		List<SavedPostSimpleResponse> result = savedPostService.findSavedIdeaMarkets(userId);
		return ResponseEntity.ok(ApiResponse.success(result));
	}

	@GetMapping("/collaboration-hubs")
	public ResponseEntity<ApiResponse<List<SavedPostSimpleResponse>>> getSavedCollaborationHubs(
		@RequestParam long userId) {
		List<SavedPostSimpleResponse> result = savedPostService.findSavedCollaborationHubs(userId);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
}