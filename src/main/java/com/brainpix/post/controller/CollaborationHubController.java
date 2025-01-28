package com.brainpix.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.converter.ApplyCollaborationDtoConverter;
import com.brainpix.post.dto.ApplyCollaborationDto;
import com.brainpix.post.service.CollaborationHubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collaborations")
@RequiredArgsConstructor
public class CollaborationHubController {

	private final CollaborationHubService collaborationHubService;

	@PostMapping("/{collaborationId}/apply")
	public ResponseEntity<ApiResponse<Void>> applyCollaboration(@PathVariable("collaborationId") Long collaborationId,
		@RequestParam("userId") Long userId, ApplyCollaborationDto.Request request) {
		ApplyCollaborationDto.Parameter parameter = ApplyCollaborationDtoConverter.toParameter(collaborationId, userId,
			request);
		collaborationHubService.applyCollaboration(parameter);
		return ResponseEntity.ok(ApiResponse.createdWithNoData());
	}
}
