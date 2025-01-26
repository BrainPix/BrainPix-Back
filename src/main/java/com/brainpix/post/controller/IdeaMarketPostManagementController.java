package com.brainpix.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.MyIdeaMarketPostDetailDto;
import com.brainpix.post.dto.MyIdeaMarketPostDto;
import com.brainpix.post.service.MyIdeaMarketPostManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post-management/idea-market")
@RequiredArgsConstructor
public class IdeaMarketPostManagementController {

	private final MyIdeaMarketPostManagementService postManagementService;

	/**
	 * 내가 작성한 아이디어마켓 게시글 목록
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<List<MyIdeaMarketPostDto>>> getMyIdeaMarketPosts(
		@RequestParam Long userId
	) {
		List<MyIdeaMarketPostDto> dtos = postManagementService.getMyIdeaMarketPosts(userId);
		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	/**
	 * 게시글 상세 (구매자 목록 포함)
	 */
	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<MyIdeaMarketPostDetailDto>> getIdeaMarketPostDetail(
		@RequestParam Long userId,
		@PathVariable Long postId
	) {
		MyIdeaMarketPostDetailDto detailDto = postManagementService.getIdeaMarketDetail(userId, postId);
		return ResponseEntity.ok(ApiResponse.success(detailDto));
	}
}
