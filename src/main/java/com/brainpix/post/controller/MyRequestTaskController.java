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
import com.brainpix.post.dto.PostRequestTaskResponse;
import com.brainpix.post.dto.mypostdto.MyRequestTaskDetailResponse;
import com.brainpix.post.service.mypost.MyRequestTaskService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "나의 요청 과제 조회", description = "내가 작성한 요청과제 조회 API")
@RequestMapping("/post-management/request-task")
public class MyRequestTaskController {
	private final MyRequestTaskService myRequestTaskService;

	@Operation(summary = "나의 요청 과제 조회", description = "현재 로그인한 사용자가 본인이 작성한 요청 과제 리스트를 조회합니다.")
	@AllUser
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<PostRequestTaskResponse>>> getSavedIdeaMarkets(
		@UserId Long userId, Pageable pageable) {
		Page<PostRequestTaskResponse> result = myRequestTaskService.findReqeustTaskPosts(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(CommonPageResponse.of(result)));
	}

	@Operation(summary = "나의 요청 과제 상세 조회", description = "현재 로그인한 사용자가 본인이 작성한 요청과제에 대해 상세조회합니다(지원현황,현재인원)")
	@AllUser
	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<MyRequestTaskDetailResponse>> getRequestTaskDetail(
		@UserId Long userId,
		@PathVariable Long postId
	) {
		MyRequestTaskDetailResponse response = myRequestTaskService.getRequestTaskDetail(userId, postId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Operation(summary = "지원 수락", description = "해당 지원을 승인합니다")
	@AllUser
	@PostMapping("/application/{purchasingId}/accept")
	public ResponseEntity<ApiResponse<Void>> acceptApplication(
		@UserId Long userId,
		@PathVariable Long purchasingId
	) {
		myRequestTaskService.acceptApplication(userId, purchasingId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "지원 거절", description = "해당 지원을 거절합니다")
	@AllUser
	@PostMapping("/application/{purchasingId}/reject")
	public ResponseEntity<ApiResponse<Void>> rejectApplication(
		@UserId Long userId,
		@PathVariable Long purchasingId
	) {
		myRequestTaskService.rejectApplication(userId, purchasingId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

}
