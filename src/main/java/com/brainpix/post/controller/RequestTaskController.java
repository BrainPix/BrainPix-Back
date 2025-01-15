package com.brainpix.post.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.service.RequestTaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class RequestTaskController {

	private final RequestTaskService requestTaskService;

	@PostMapping
	public ResponseEntity<ApiResponse> createRequestTask(@RequestBody RequestTaskCreateDto createDto) {
		Long taskId = requestTaskService.createRequestTask(createDto);
		return ResponseEntity.ok(ApiResponse.success(Map.of("taskId", taskId)));
	}

	@PatchMapping("/{taskId}")
	public ResponseEntity<ApiResponse> updateRequestTask(@PathVariable("taskId") Long id, @RequestBody RequestTaskUpdateDto updateDto) {
		requestTaskService.updateRequestTask(id, updateDto);
		return ResponseEntity.ok(ApiResponse.success("요청 과제가 성공적으로 수정되었습니다."));
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<ApiResponse> deleteRequestTask(@PathVariable("taskId") Long id) {
		requestTaskService.deleteRequestTask(id);
		return ResponseEntity.ok(ApiResponse.success("요청 과제가 성공적으로 삭제되었습니다."));
	}
}