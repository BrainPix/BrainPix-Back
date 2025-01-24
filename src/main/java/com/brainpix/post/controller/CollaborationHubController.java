package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.converter.GetCollaborationHubDetailDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubListDtoConverter;
import com.brainpix.post.dto.GetCollaborationHubDetailDto;
import com.brainpix.post.dto.GetCollaborationHubListDto;
import com.brainpix.post.service.CollaborationHubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collaborations")
@RequiredArgsConstructor
public class CollaborationHubController {

	private final CollaborationHubService collaborationHubService;

	@GetMapping
	public ResponseEntity<ApiResponse<GetCollaborationHubListDto.Response>> getCollaborationHubList(
		GetCollaborationHubListDto.Request request, Pageable pageable) {
		GetCollaborationHubListDto.Parameter parameter = GetCollaborationHubListDtoConverter.toParameter(request,
			pageable);
		GetCollaborationHubListDto.Response response = collaborationHubService.getCollaborationHubList(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
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