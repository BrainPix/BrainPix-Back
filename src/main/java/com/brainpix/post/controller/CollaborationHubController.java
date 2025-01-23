package com.brainpix.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.CollaborationHubApiResponseDto;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;
import com.brainpix.post.service.CollaborationHubService;
import com.brainpix.post.service.CollaborationHubProjectMemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class CollaborationHubController {

	private final CollaborationHubService collaborationHubService;
	private final CollaborationHubProjectMemberService collaborationHubProjectMemberService;


	@PostMapping
	public ResponseEntity<ApiResponse<CollaborationHubApiResponseDto>> createCollaborationHub(@RequestParam Long userId, @Valid @RequestBody CollaborationHubCreateDto createDto) {
		Long workspaceId = collaborationHubService.createCollaborationHub(userId, createDto);
		return ResponseEntity.ok(ApiResponse.success(new CollaborationHubApiResponseDto("workspaceId", workspaceId)));
	}

	@PatchMapping("/{workspaceId}")
	public ResponseEntity<ApiResponse<Void>> updateCollaborationHub(@PathVariable("workspaceId") Long workspaceId, @RequestParam Long userId, @Valid @RequestBody CollaborationHubUpdateDto updateDto) {
		collaborationHubService.updateCollaborationHub(workspaceId, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());

	}

	@DeleteMapping("/{workspaceId}")
	public ResponseEntity<ApiResponse<Void>> deleteCollaborationHub(@PathVariable("workspaceId") Long workspaceId, @RequestParam Long userId) {
		collaborationHubService.deleteCollaborationHub(workspaceId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());

	}

	@GetMapping("/validate/{userId}")
	public ResponseEntity<ApiResponse<Void>> validateUserId(@PathVariable Long userId) {
		collaborationHubProjectMemberService.validateUserId(userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}