package com.brainpix.post.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;
import com.brainpix.post.service.CollaborationHubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class CollaborationHubController {

	private final CollaborationHubService collaborationHubService;

	@PostMapping
	public ResponseEntity<ApiResponse> createCollaborationHub(@RequestBody CollaborationHubCreateDto createDto) {
		Long workspaceId = collaborationHubService.createCollaborationHub(createDto);
		return ResponseEntity.ok(ApiResponse.success(Map.of("workspaceId", workspaceId)));
	}

	@PatchMapping("/{workspaceId}")
	public ResponseEntity<ApiResponse> updateCollaborationHub(@PathVariable("workspaceId") Long id, @RequestBody CollaborationHubUpdateDto updateDto) {
		collaborationHubService.updateCollaborationHub(id, updateDto);
		return ResponseEntity.ok(ApiResponse.success("협업 광장 게시글이 성공적으로 수정되었습니다."));
	}

	@DeleteMapping("/{workspaceId}")
	public ResponseEntity<ApiResponse> deleteCollaborationHub(@PathVariable("workspaceId") Long id) {
		collaborationHubService.deleteCollaborationHub(id);
		return ResponseEntity.ok(ApiResponse.success("협업 광장 게시글이 성공적으로 삭제되었습니다."));
	}
}