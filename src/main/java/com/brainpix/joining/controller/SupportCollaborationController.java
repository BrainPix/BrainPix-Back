package com.brainpix.joining.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.joining.dto.AcceptedCollaborationDto;
import com.brainpix.joining.dto.RejectedCollaborationDto;
import com.brainpix.joining.service.SupportCollaborationService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supports/collaborations")
@RequiredArgsConstructor
public class SupportCollaborationController {

	private final SupportCollaborationService supportCollaborationService;

	@AllUser
	@GetMapping("/rejected")
	public ResponseEntity<ApiResponse<Page<RejectedCollaborationDto>>> getRejectedList(
		@UserId Long userId,
		Pageable pageable) {

		Page<RejectedCollaborationDto> dtos = supportCollaborationService.getRejectedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	@AllUser
	@GetMapping("/accepted")
	public ResponseEntity<ApiResponse<Page<AcceptedCollaborationDto>>> getAcceptedList(
		@UserId Long userId,
		Pageable pageable) {

		Page<AcceptedCollaborationDto> dtos = supportCollaborationService.getAcceptedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	/**
	 * [DELETE] 거절 항목 삭제
	 */
	@AllUser
	@DeleteMapping("/{collectionGatheringId}")
	public ResponseEntity<ApiResponse<Void>> deleteRejected(
		@PathVariable Long collectionGatheringId,
		@UserId Long userId) {

		supportCollaborationService.deleteRejected(userId, collectionGatheringId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
