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

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supports/collaborations")
@RequiredArgsConstructor
public class SupportCollaborationController {

	private final SupportCollaborationService supportCollaborationService;

	@Operation(summary = "지원 거절된 협업광장 게시글", description = "본인이 지원한 협업광장 게시글 중 거절된 목록들을 조회합니다.")
	@AllUser
	@GetMapping("/rejected")
	public ResponseEntity<ApiResponse<Page<RejectedCollaborationDto>>> getRejectedList(
		@UserId Long userId,
		Pageable pageable) {

		Page<RejectedCollaborationDto> dtos = supportCollaborationService.getRejectedList(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(dtos));
	}

	@Operation(summary = "지원 승낙된 협업광장 게시글", description = "본인이 지원한 협업광장 게시글 중 승낙된 목록들을 조회합니다.")
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
	@Operation(summary = "지원 거절된 협업광장 게시글 삭제", description = "지원 거절당한 협업 광장 게시글을 삭제합니다.")
	@AllUser
	@DeleteMapping("/{collectionGatheringId}")
	public ResponseEntity<ApiResponse<Void>> deleteRejected(
		@PathVariable Long collectionGatheringId,
		@UserId Long userId) {

		supportCollaborationService.deleteRejected(userId, collectionGatheringId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
