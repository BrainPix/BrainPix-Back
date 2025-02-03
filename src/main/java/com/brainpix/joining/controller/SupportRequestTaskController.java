package com.brainpix.joining.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.joining.dto.AcceptedRequestTaskPurchasingDto;
import com.brainpix.joining.dto.RejectedRequestTaskPurchasingDto;
import com.brainpix.joining.service.SupportRequestTaskService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supports/request-tasks")
@RequiredArgsConstructor
public class SupportRequestTaskController {

	private final SupportRequestTaskService supportRequestTaskService;

	@Operation(summary = "지원 거절된 요청 과제 게시글", description = "본인이 지원한 요청과제 게시글 중 거절된 목록들을 조회합니다.")
	@AllUser
	@GetMapping("/rejected")
	public ResponseEntity<ApiResponse<CommonPageResponse<RejectedRequestTaskPurchasingDto>>> getRejectedList(
		@UserId Long userId,
		Pageable pageable
	) {
		CommonPageResponse<RejectedRequestTaskPurchasingDto> response =
			supportRequestTaskService.getRejectedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Operation(summary = "지원 승낙된 요청 과제 게시글", description = "본인이 지원한 요청과제 게시글 중 승낙된 목록들을 조회합니다.")
	@AllUser
	@GetMapping("/accepted")
	public ResponseEntity<ApiResponse<CommonPageResponse<AcceptedRequestTaskPurchasingDto>>> getAcceptedList(
		@UserId Long userId,
		Pageable pageable
	) {
		CommonPageResponse<AcceptedRequestTaskPurchasingDto> response =
			supportRequestTaskService.getAcceptedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Operation(summary = "지원 거절된 요청 과제 게시글 삭제", description = "본인이 지원한 요청과제 게시글 중 거절된 게시글을 삭제합니다.")
	@AllUser
	@DeleteMapping("/{purchasingId}")
	public ResponseEntity<ApiResponse<Void>> deleteRejectedPurchasing(
		@PathVariable Long purchasingId,
		@UserId Long userId) {
		supportRequestTaskService.deleteRejectedPurchasing(userId, purchasingId);
		return ResponseEntity
			.ok(ApiResponse.successWithNoData());
	}
}
