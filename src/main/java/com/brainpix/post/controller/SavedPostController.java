package com.brainpix.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.post.dto.SavedPostSimpleResponse;
import com.brainpix.post.service.SavedPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

	private final SavedPostService savedPostService;

	@PostMapping
	public ResponseEntity<String> savePost(@RequestParam long userId, @RequestParam long postId) {
		try {
			savedPostService.savePost(userId, postId);
			return ResponseEntity.ok("게시물이 성공적으로 저장되었습니다.");
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/request-tasks")
	public ResponseEntity<List<SavedPostSimpleResponse>> getSavedRequestTasks(@RequestParam long userId) {
		List<SavedPostSimpleResponse> result = savedPostService.findSavedRequestTasks(userId);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/idea-markets")
	public ResponseEntity<List<SavedPostSimpleResponse>> getSavedIdeaMarkets(@RequestParam long userId) {
		List<SavedPostSimpleResponse> result = savedPostService.findSavedIdeaMarkets(userId);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/collaboration-hubs")
	public ResponseEntity<List<SavedPostSimpleResponse>> getSavedCollaborationHubs(@RequestParam long userId) {
		List<SavedPostSimpleResponse> result = savedPostService.findSavedCollaborationHubs(userId);
		return ResponseEntity.ok(result);
	}
}
