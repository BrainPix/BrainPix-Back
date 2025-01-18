package com.brainpix.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
