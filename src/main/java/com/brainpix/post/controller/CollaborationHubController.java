package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.converter.ApplyCollaborationDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubDetailDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubListDtoConverter;
import com.brainpix.post.dto.ApplyCollaborationDto;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;
import com.brainpix.post.dto.GetCollaborationHubDetailDto;
import com.brainpix.post.dto.GetCollaborationHubListDto;
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
@Tag(name = "CollaborationHub API", description = "협업 광장 관련 API")
public class CollaborationHubController {

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
	@Operation(summary = "협업 광장 신청 API", description = "경로 변수로 협업 게시글 식별자 값을 입력받고, json body로 지원 분야 식별자, 프로필 공개 여부, 추가 메시지를 입력받습니다.")
	@PostMapping("/{collaborationId}/apply")
	public ResponseEntity<ApiResponse<ApplyCollaborationDto.Response>> applyCollaboration(
		@PathVariable("collaborationId") Long collaborationId,
		@RequestParam("userId") Long userId,
		@RequestBody ApplyCollaborationDto.Request request) {
		ApplyCollaborationDto.Parameter parameter = ApplyCollaborationDtoConverter.toParameter(collaborationId, userId,
			request);
		ApplyCollaborationDto.Response response = collaborationHubService.applyCollaboration(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "협업 광장 글 수정", description = "협업 광장 게시글 내용을 수정합니다. 모집 분야 및 개최 인원 정보는 수정할 수 없습니다.")
	@PutMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<Void>> updateCollaborationHub(@PathVariable("collaborationId") Long workspaceId,
		@UserId Long userId, @Valid @RequestBody CollaborationHubUpdateDto updateDto) {
		collaborationHubService.updateCollaborationHub(workspaceId, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "협업 광장 전체 조회 [POST]", description = "json body로 검색 조건을 입력받고, 쿼리 파라미터로 페이징을 위한 page, size를 입력받아 전체 조회합니다.")
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetCollaborationHubListDto.CollaborationDetail>>> getCollaborationHubList(
		@RequestBody GetCollaborationHubListDto.Request request,
		@PageableDefault(page = 0, size = 6) Pageable pageable) {
		GetCollaborationHubListDto.Parameter parameter = GetCollaborationHubListDtoConverter.toParameter(request,
			pageable);
		CommonPageResponse<GetCollaborationHubListDto.CollaborationDetail> response = collaborationHubService.getCollaborationHubList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "협업 광장 글 삭제", description = "협업 광장 게시글을 삭제합니다.")
	@DeleteMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<Void>> deleteCollaborationHub(@PathVariable("collaborationId") Long workspaceId,
		@UserId Long userId) {
		collaborationHubService.deleteCollaborationHub(workspaceId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "협업 광장 內 개최 인원 아이디 검증", description = "개최 인원 정보 파트에 아이디 입력 후 포트폴리오 불러오기를 통해 아이디의 존재 여부를 검증합니다.")
	@GetMapping("/validate/{identifier}")
	public ResponseEntity<ApiResponse<Void>> validateUserIdentifier(@PathVariable String identifier) {
		collaborationHubInitialMemberService.validateUserIdentifier(identifier);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@Operation(summary = "협업 광장 상세 조회 [GET]", description = "경로 변수로 협업 게시글 식별자 값을 입력받아 상세 조회합니다.")
	@GetMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<GetCollaborationHubDetailDto.Response>> getCollaborationHubDetail(
		@UserId Long userId,
		@PathVariable("collaborationId") Long collaborationId) {
		GetCollaborationHubDetailDto.Parameter parameter = GetCollaborationHubDetailDtoConverter.toParameter(
			collaborationId, userId);
		GetCollaborationHubDetailDto.Response response = collaborationHubService.getCollaborationHubDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}