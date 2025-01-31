package com.brainpix.joining.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.joining.dto.AcceptedRequestTaskPurchasingDto;
import com.brainpix.joining.dto.RejectedRequestTaskPurchasingDto;
import com.brainpix.joining.service.SupportRequestTaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supports/request-tasks")
@RequiredArgsConstructor
public class SupportRequestTaskController {

	private final SupportRequestTaskService supportRequestTaskService;

	@GetMapping("/rejected")
	public ResponseEntity<ApiResponse<CommonPageResponse<RejectedRequestTaskPurchasingDto>>> getRejectedList(
		@RequestParam Long userId,
		Pageable pageable
	) {
		CommonPageResponse<RejectedRequestTaskPurchasingDto> response =
			supportRequestTaskService.getRejectedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/accepted")
	public ResponseEntity<ApiResponse<CommonPageResponse<AcceptedRequestTaskPurchasingDto>>> getAcceptedList(
		@RequestParam Long userId,
		Pageable pageable
	) {
		CommonPageResponse<AcceptedRequestTaskPurchasingDto> response =
			supportRequestTaskService.getAcceptedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@DeleteMapping("/{purchasingId}")
	public ResponseEntity<ApiResponse<Void>> deleteRejectedPurchasing(
		@PathVariable Long purchasingId,
		@RequestParam Long userId) {
		supportRequestTaskService.deleteRejectedPurchasing(userId, purchasingId);
		return ResponseEntity
			.ok(ApiResponse.successWithNoData());
	}
}
