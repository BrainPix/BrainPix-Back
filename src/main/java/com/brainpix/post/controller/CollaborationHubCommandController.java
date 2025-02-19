package com.brainpix.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;
import com.brainpix.post.dto.PostApiResponseDto;
import com.brainpix.post.service.CollaborationHubInitialMemberService;
import com.brainpix.post.service.CollaborationHubService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collaborations")
@RequiredArgsConstructor
@Tag(name = "협업 광장 생성, 수정, 삭제 API", description = "협업 광장 관련 API")
public class CollaborationHubCommandController {

	private final CollaborationHubService collaborationHubService;
	private final CollaborationHubInitialMemberService collaborationHubInitialMemberService;

	@AllUser
	@Operation(summary = "협업 광장 글 생성", description = "협업 광장 글 내용, 모집 분야와 개최 인원 정보를 포함하여 협업 광장 게시글을 생성합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<PostApiResponseDto>> createCollaborationHub(@UserId Long userId,
		@Valid @RequestBody CollaborationHubCreateDto createDto) {
		Long collaborationId = collaborationHubService.createCollaborationHub(userId, createDto);
		return ResponseEntity.ok(
			ApiResponse.success(new PostApiResponseDto("collaborationId", collaborationId)));
	}

	@AllUser
	@Operation(summary = "협업 광장 글 수정", description = "협업 광장 게시글 내용을 수정합니다. 모집 분야 및 개최 인원 정보는 수정할 수 없습니다.")
	@PutMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<Void>> updateCollaborationHub(@PathVariable("collaborationId") Long workspaceId,
		@UserId Long userId, @Valid @RequestBody CollaborationHubUpdateDto updateDto) {
		collaborationHubService.updateCollaborationHub(workspaceId, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@Operation(summary = "협업 광장 글 삭제", description = "협업 광장 게시글을 삭제합니다.")
	@DeleteMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<Void>> deleteCollaborationHub(@PathVariable("collaborationId") Long workspaceId,
		@UserId Long userId) {
		collaborationHubService.deleteCollaborationHub(workspaceId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "협업 광장 內 개최 인원 유저 고유 아이디 반환", description = "개최 인원 정보 파트에 로그인 아이디 입력 후 포트폴리오 불러오기를 통해 유저 고유 아이디를 반환하여 유저 프로필에 접근할 수 있도록 합니다.")
	@GetMapping("/validate/{identifier}")
	public ResponseEntity<ApiResponse<Long>> validateUserIdentifier(@PathVariable String identifier) {
		Long userId = collaborationHubInitialMemberService.validateUserIdentifier(identifier);
		return ResponseEntity.ok(ApiResponse.success(userId));
	}
}