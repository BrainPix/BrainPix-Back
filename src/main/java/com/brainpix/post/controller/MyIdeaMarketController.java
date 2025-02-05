package com.brainpix.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.dto.PostIdeaMarketResponse;
import com.brainpix.post.dto.mypostdto.MyIdeaMarketPostDetailResponse;
import com.brainpix.post.service.mypost.MyIdeaMarketService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post-management/idea-market")
@RequiredArgsConstructor
public class MyIdeaMarketController {

	private final MyIdeaMarketService myIdeaMarketService;

	@Operation(summary = "나의 아이디어 마켓 조회", description = "현재 로그인한 사용자가 본인이 작성한 아이디어 마켓리스트를 조회합니다.")
	@AllUser
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<PostIdeaMarketResponse>>> getSavedIdeaMarkets(
		@UserId Long userId, Pageable pageable) {
		Page<PostIdeaMarketResponse> result = myIdeaMarketService.findIdeaMarketPosts(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@Operation(summary = "나의 아이디어 마켓 상세 조회", description = "본인이 작성한 아이디어 마켓 상세 조회를 합니다.")
	@AllUser
	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<MyIdeaMarketPostDetailResponse>> getIdeaMarketPostDetail(
		@UserId Long userId,
		@PathVariable Long postId
	) {
		MyIdeaMarketPostDetailResponse response = myIdeaMarketService.getMyIdeaMarketPostDetail(userId, postId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

}
