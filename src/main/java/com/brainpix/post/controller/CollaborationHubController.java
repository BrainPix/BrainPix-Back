package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
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
import com.brainpix.post.converter.ApplyCollaborationDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubDetailDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubListDtoConverter;
import com.brainpix.post.dto.ApplyCollaborationDto;
import com.brainpix.post.dto.CollaborationHubApiResponseDto;
import com.brainpix.post.dto.CollaborationHubCreateDto;
import com.brainpix.post.dto.CollaborationHubUpdateDto;
import com.brainpix.post.dto.GetCollaborationHubDetailDto;
import com.brainpix.post.dto.GetCollaborationHubListDto;
import com.brainpix.post.service.CollaborationHubInitialMemberService;
import com.brainpix.post.service.CollaborationHubService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collaborations")
@RequiredArgsConstructor
public class CollaborationHubController {

	private final CollaborationHubService collaborationHubService;
	private final CollaborationHubInitialMemberService collaborationHubInitialMemberService;

	@PostMapping
	public ResponseEntity<ApiResponse<CollaborationHubApiResponseDto>> createCollaborationHub(@RequestParam Long userId,
		@Valid @RequestBody CollaborationHubCreateDto createDto) {
		Long collaborationId = collaborationHubService.createCollaborationHub(userId, createDto);
		return ResponseEntity.ok(
			ApiResponse.success(new CollaborationHubApiResponseDto("collaborationId", collaborationId)));
	}

	@PostMapping("/{collaborationId}/apply")
	public ResponseEntity<ApiResponse<Void>> applyCollaboration(@PathVariable("collaborationId") Long collaborationId,
		@RequestParam("userId") Long userId, ApplyCollaborationDto.Request request) {
		ApplyCollaborationDto.Parameter parameter = ApplyCollaborationDtoConverter.toParameter(collaborationId, userId,
			request);
		collaborationHubService.applyCollaboration(parameter);
		return ResponseEntity.ok(ApiResponse.createdWithNoData());
	}

	@PutMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<Void>> updateCollaborationHub(@PathVariable("collaborationId") Long workspaceId,
		@RequestParam Long userId, @Valid @RequestBody CollaborationHubUpdateDto updateDto) {
		collaborationHubService.updateCollaborationHub(workspaceId, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping
	public ResponseEntity<ApiResponse<GetCollaborationHubListDto.Response>> getCollaborationHubList(
		GetCollaborationHubListDto.Request request, Pageable pageable) {
		GetCollaborationHubListDto.Parameter parameter = GetCollaborationHubListDtoConverter.toParameter(request,
			pageable);
		GetCollaborationHubListDto.Response response = collaborationHubService.getCollaborationHubList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@DeleteMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<Void>> deleteCollaborationHub(@PathVariable("collaborationId") Long workspaceId,
		@RequestParam Long userId) {
		collaborationHubService.deleteCollaborationHub(workspaceId, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping("/validate/{identifier}")
	public ResponseEntity<ApiResponse<Void>> validateUserIdentifier(@PathVariable String identifier) {
		collaborationHubInitialMemberService.validateUserIdentifier(identifier);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<GetCollaborationHubDetailDto.Response>> getCollaborationHubDetail(
		@PathVariable("collaborationId") Long collaborationId) {
		GetCollaborationHubDetailDto.Parameter parameter = GetCollaborationHubDetailDtoConverter.toParameter(
			collaborationId);
		GetCollaborationHubDetailDto.Response response = collaborationHubService.getCollaborationHubDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}