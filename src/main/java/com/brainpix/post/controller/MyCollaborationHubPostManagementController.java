package com.brainpix.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
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
	public ResponseEntity<ApiResponse<CommonPageResponse<MyCollaborationHubListDto>>> getCollaborationHubList(
		@RequestParam Long userId,
		Pageable pageable
	) {
		Page<MyCollaborationHubListDto> hubs = collaborationHubService.getAllCollaborationHubs(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(hubs)));
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

	/**
	 * 지원자 수락
	 */
	@PostMapping("/accept")
	public ResponseEntity<ApiResponse<Void>> acceptSupport(
		@RequestParam Long userId,
		@RequestParam Long gatheringId
	) {
		collaborationHubService.acceptSupport(userId, gatheringId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	/**
	 * 지원자 거절
	 */
	@PostMapping("/reject")
	public ResponseEntity<ApiResponse<Void>> rejectSupport(
		@RequestParam Long userId,
		@RequestParam Long gatheringId
	) {
		collaborationHubService.rejectSupport(userId, gatheringId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

}
