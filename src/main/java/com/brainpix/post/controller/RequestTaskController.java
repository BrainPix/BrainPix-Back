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
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.converter.GetPopularRequestTaskListDtoConverter;
import com.brainpix.post.converter.GetRequestTaskDetailDtoConverter;
import com.brainpix.post.converter.GetRequestTaskListDtoConverter;
import com.brainpix.post.dto.GetPopularRequestTaskListDto;
import com.brainpix.post.dto.GetRequestTaskDetailDto;
import com.brainpix.post.dto.GetRequestTaskListDto;
import com.brainpix.post.dto.RequestTaskApiResponseDto;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.service.RequestTaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class RequestTaskController {

	private final RequestTaskService requestTaskService;

	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail>>> getRequestTaskList(
		GetRequestTaskListDto.Request request, Pageable pageable) {
		GetRequestTaskListDto.Parameter parameter = GetRequestTaskListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail> response = requestTaskService.getRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail>>> getPopularRequestTaskList(
		GetPopularRequestTaskListDto.Request request, Pageable pageable) {
		GetPopularRequestTaskListDto.Parameter parameter = GetPopularRequestTaskListDtoConverter.toParameter(request,
			pageable);
		CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail> response = requestTaskService.getPopularRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<ApiResponse<GetRequestTaskDetailDto.Response>> getRequestTaskDetail(
		@PathVariable("taskId") Long taskId
	) {
		GetRequestTaskDetailDto.Parameter parameter = GetRequestTaskDetailDtoConverter.toParameter(taskId);
		GetRequestTaskDetailDto.Response response = requestTaskService.getRequestTaskDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<RequestTaskApiResponseDto>> createRequestTask(@RequestParam Long userId,
		@Valid @RequestBody RequestTaskCreateDto createDto) {
		Long taskId = requestTaskService.createRequestTask(userId, createDto); // 컨버터행
		return ResponseEntity.ok(ApiResponse.success(new RequestTaskApiResponseDto("taskId", taskId)));
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<ApiResponse<Void>> updateRequestTask(@PathVariable("taskId") Long taskid,
		@RequestParam Long userId, @Valid @RequestBody RequestTaskUpdateDto updateDto) {
		requestTaskService.updateRequestTask(taskid, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<ApiResponse<Void>> deleteRequestTask(@PathVariable("taskId") Long taskid,
		@RequestParam Long userId) {
		requestTaskService.deleteRequestTask(taskid, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
