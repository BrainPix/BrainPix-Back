package com.brainpix.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.dto.PostCollaborationResponse;
import com.brainpix.post.dto.mypostdto.MyCollaborationHubDetailResponse;
import com.brainpix.post.service.mypost.MyCollaborationHubService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post-management/collaboration")
@RequiredArgsConstructor
public class MyCollaborationController {

	private final MyCollaborationHubService myCollaborationHubService;

	@Operation(summary = "나의 협업 광장 조회", description = "현재 로그인한 사용자가 본인이 작성한 협업광장 리스트를 조회합니다.")
	@AllUser
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<PostCollaborationResponse>>> getSavedIdeaMarkets(
		@UserId Long userId, Pageable pageable) {
		Page<PostCollaborationResponse> result = myCollaborationHubService.findCollaborationPosts(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@Operation(summary = "나의 협업광장 상세 조회", description = "현재 로그인한 사용자가 본인이 작성한 협업광장 게시글의 상세정보(지원 현황, 현재 승인된 인원 포함)를 조회합니다.")
	@AllUser
	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<MyCollaborationHubDetailResponse>> getCollaborationHubDetail(
		@UserId Long userId,
		@PathVariable Long postId
	) {
		MyCollaborationHubDetailResponse response = myCollaborationHubService.getCollaborationHubDetail(userId, postId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Operation(summary = "지원 수락", description = "협업광장의 특정 지원 요청을 승인합니다.")
	@AllUser
	@PostMapping("/application/{gatheringId}/accept")
	public ResponseEntity<ApiResponse<Void>> acceptApplication(
		@UserId Long userId,
		@PathVariable Long gatheringId
	) {
		myCollaborationHubService.acceptApplication(userId, gatheringId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "지원 거절", description = "협업광장의 특정 지원 요청을 거절합니다.")
	@AllUser
	@PostMapping("/application/{gatheringId}/reject")
	public ResponseEntity<ApiResponse<Void>> rejectApplication(
		@UserId Long userId,
		@PathVariable Long gatheringId
	) {
		myCollaborationHubService.rejectApplication(userId, gatheringId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
