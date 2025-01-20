package com.brainpix.post.controller;

import java.util.Map;

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
	public ResponseEntity<ApiResponse> createRequestTask(@RequestParam Long userId, RequestTaskCreateDto createDto) {
		Long taskId = requestTaskService.createRequestTask(userId, createDto);
		return ResponseEntity.ok(ApiResponse.created(Map.of("taskId", taskId)));
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<ApiResponse> updateRequestTask(@PathVariable("taskId") Long taskid, @RequestBody RequestTaskUpdateDto updateDto) {
		requestTaskService.updateRequestTask(taskid, updateDto);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<ApiResponse> deleteRequestTask(@PathVariable("taskId") Long taskid) {
		requestTaskService.deleteRequestTask(taskid);
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
