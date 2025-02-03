package com.brainpix.joining.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.joining.dto.AcceptedCollaborationDto;
import com.brainpix.joining.dto.RejectedCollaborationDto;
import com.brainpix.joining.service.SupportCollaborationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supports/collaborations")
@RequiredArgsConstructor
public class SupportCollaborationController {

	private final SupportCollaborationService supportCollaborationService;

	@GetMapping("/rejected")
	public ResponseEntity<ApiResponse<Page<RejectedCollaborationDto>>> getRejectedList(
		@RequestParam Long userId,
		Pageable pageable) {

		Page<RejectedCollaborationDto> dtos = supportCollaborationService.getRejectedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	@GetMapping("/accepted")
	public ResponseEntity<ApiResponse<Page<AcceptedCollaborationDto>>> getAcceptedList(
		@RequestParam Long userId,
		Pageable pageable) {

		Page<AcceptedCollaborationDto> dtos = supportCollaborationService.getAcceptedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	/**
	 * [DELETE] 거절 항목 삭제
	 */
	@DeleteMapping("/{collectionGatheringId}")
	public ResponseEntity<ApiResponse<Void>> deleteRejected(
		@PathVariable Long collectionGatheringId,
		@RequestParam Long userId) {

		supportCollaborationService.deleteRejected(userId, collectionGatheringId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
