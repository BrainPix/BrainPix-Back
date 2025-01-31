package com.brainpix.post.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.post.service.RequestTaskRecruitmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks/{taskId}/recruitments")
@RequiredArgsConstructor
public class RequestTaskRecruitmentController {

	private final RequestTaskRecruitmentService recruitmentService;

	/*
	@PutMapping("/{recruitmentId}")
	public ResponseEntity<ApiResponse> updateRecruitments(@PathVariable Long recruitmentId, @RequestBody List<RequestTaskRecruitmentDto> recruitmentDtos) {
		recruitmentService.updateRecruitments(recruitmentId, recruitmentDtos);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
	 */
}
