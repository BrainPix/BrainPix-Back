package com.brainpix.joining.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<List<RejectedRequestTaskPurchasingDto>> getRejectedList(
		@RequestParam Long userId) {
		List<RejectedRequestTaskPurchasingDto> dtos =
			supportRequestTaskService.getRejectedList(userId);
		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/accepted")
	public ResponseEntity<List<AcceptedRequestTaskPurchasingDto>> getAcceptedList(
		@RequestParam Long userId) {
		List<AcceptedRequestTaskPurchasingDto> dtos =
			supportRequestTaskService.getAcceptedList(userId);
		return ResponseEntity.ok(dtos);
	}

	@DeleteMapping("/{purchasingId}")
	public ResponseEntity<Void> deleteRejectedPurchasing(
		@PathVariable Long purchasingId,
		@RequestParam Long userId) {
		supportRequestTaskService.deleteRejectedPurchasing(userId, purchasingId);
		return ResponseEntity.noContent().build();
	}
}
