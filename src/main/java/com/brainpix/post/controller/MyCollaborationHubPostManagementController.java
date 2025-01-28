package com.brainpix.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubDetailDto;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubListDto;
import com.brainpix.post.service.MyCollaborationPostManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post-management/collaboration-hub")
@RequiredArgsConstructor
public class MyCollaborationHubPostManagementController {

	private final MyCollaborationPostManagementService collaborationHubService;

	/**
	 * 협업 광장 게시물 전체 보기
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<List<MyCollaborationHubListDto>>> getCollaborationHubList(
		@RequestParam Long userId
	) {
		List<MyCollaborationHubListDto> hubs = collaborationHubService.getAllCollaborationHubs(userId);
		return ResponseEntity.ok(ApiResponse.success(hubs));
	}

	/**
	 * 협업 광장 게시물 상세 보기
	 */
	@GetMapping("/{hubId}")
	public ResponseEntity<ApiResponse<MyCollaborationHubDetailDto>> getCollaborationHubDetail(
		@PathVariable Long hubId
	) {
		MyCollaborationHubDetailDto hubDetail = collaborationHubService.getCollaborationHubDetail(hubId);
		return ResponseEntity.ok(ApiResponse.success(hubDetail));
	}
}
