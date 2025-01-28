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
import com.brainpix.post.dto.mypostdto.MyRequestTaskPostDetailDto;
import com.brainpix.post.dto.mypostdto.MyRequestTaskPostDto;
import com.brainpix.post.service.MyRequestTaskPostManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post-management/request-task")
@RequiredArgsConstructor
public class MyRequestTaskPostManagementController {

	private final MyRequestTaskPostManagementService service;

	/**
	 *  내가 작성한 요청 과제 목록
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<MyRequestTaskPostDto>>> getMyRequestTasks(
		@RequestParam Long userId,
		Pageable pageable
	) {
		Page<MyRequestTaskPostDto> dtos = service.getMyRequestTasks(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(dtos)));
	}

	/**
	 *  내가 작성한 요청 과제 상세 조회
	 */
	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<MyRequestTaskPostDetailDto>> getRequestTaskDetail(
		@PathVariable Long postId,
		@RequestParam Long userId
	) {
		MyRequestTaskPostDetailDto dtos = service.getRequestTaskDetail(postId, userId);
		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	/**
	 *  지원자 수락
	 */
	@PostMapping("/accept")
	public ResponseEntity<ApiResponse<Void>> acceptPurchasing(
		@RequestParam Long userId,
		@RequestParam Long purchasingId
	) {
		service.acceptPurchasing(userId, purchasingId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	/**
	 *  지원자 거절
	 */
	@PostMapping("/reject")
	public ResponseEntity<ApiResponse<Void>> rejectPurchasing(
		@RequestParam Long userId,
		@RequestParam Long purchasingId
	) {
		service.rejectPurchasing(userId, purchasingId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

}
