package com.brainpix.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.RequestTaskRecruitmentDto;
import com.brainpix.post.service.RequestTaskRecruitmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks/{taskId}/recruitments")
@RequiredArgsConstructor
public class RequestTaskRecruitmentController {

	private final RequestTaskRecruitmentService recruitmentService;

	@PutMapping("/{recruitmentId}")
	public ResponseEntity<ApiResponse> updateRecruitments(@PathVariable Long recruitmentId, @RequestBody List<RequestTaskRecruitmentDto> recruitmentDtos) {
		recruitmentService.updateRecruitments(recruitmentId, recruitmentDtos);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
