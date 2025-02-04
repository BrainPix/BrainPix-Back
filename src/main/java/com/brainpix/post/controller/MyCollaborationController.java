package com.brainpix.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.dto.PostCollaborationResponse;
import com.brainpix.post.service.mypost.MyCollaborationHubService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post-management/collaboration-hub")
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

}
