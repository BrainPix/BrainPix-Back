package com.brainpix.post.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.RequestTaskApiResponseDto;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.service.RequestTaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class RequestTaskController {

	private final RequestTaskService requestTaskService;

	@PostMapping
	public ResponseEntity<ApiResponse<RequestTaskApiResponseDto>> createRequestTask(@RequestParam Long userId, @Valid @RequestBody RequestTaskCreateDto createDto) {
		Long taskId = requestTaskService.createRequestTask(userId, createDto); // 컨버터행
		return ResponseEntity.ok(ApiResponse.success(new RequestTaskApiResponseDto("taskId", taskId)));
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<ApiResponse<Void>> updateRequestTask(@PathVariable("taskId") Long taskid, @RequestParam Long userId, @Valid @RequestBody RequestTaskUpdateDto updateDto) {
		requestTaskService.updateRequestTask(taskid, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<ApiResponse<Void>> deleteRequestTask(@PathVariable("taskId") Long taskid, @RequestParam Long userId) {
		requestTaskService.deleteRequestTask(taskid, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
