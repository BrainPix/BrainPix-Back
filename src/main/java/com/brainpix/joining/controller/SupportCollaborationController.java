package com.brainpix.joining.controller;

import java.util.List;

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

	/**
	 * [GET] 거절 목록
	 */
	@GetMapping("/rejected")
	public ResponseEntity<ApiResponse<List<RejectedCollaborationDto>>> getRejectedList(
		@RequestParam Long userId) {

		List<RejectedCollaborationDto> dtos =
			supportCollaborationService.getRejectedList(userId);

		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	/**
	 * [GET] 수락 목록
	 */
	@GetMapping("/accepted")
	public ResponseEntity<ApiResponse<List<AcceptedCollaborationDto>>> getAcceptedList(
		@RequestParam Long userId) {

		List<AcceptedCollaborationDto> dtos =
			supportCollaborationService.getAcceptedList(userId);

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
