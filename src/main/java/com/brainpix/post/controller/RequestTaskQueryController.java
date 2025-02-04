package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.post.converter.GetPopularRequestTaskListDtoConverter;
import com.brainpix.post.converter.GetRequestTaskDetailDtoConverter;
import com.brainpix.post.converter.GetRequestTaskListDtoConverter;
import com.brainpix.post.dto.GetPopularRequestTaskListDto;
import com.brainpix.post.dto.GetRequestTaskDetailDto;
import com.brainpix.post.dto.GetRequestTaskListDto;
import com.brainpix.post.service.RequestTaskQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/request-tasks")
@RequiredArgsConstructor
public class RequestTaskQueryController {

	private final RequestTaskQueryService requestTaskQueryService;

	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail>>> getRequestTaskList(
		GetRequestTaskListDto.Request request, Pageable pageable) {
		GetRequestTaskListDto.Parameter parameter = GetRequestTaskListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail> response = requestTaskQueryService.getRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail>>> getPopularRequestTaskList(
		GetPopularRequestTaskListDto.Request request, Pageable pageable) {
		GetPopularRequestTaskListDto.Parameter parameter = GetPopularRequestTaskListDtoConverter.toParameter(request,
			pageable);
		CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail> response = requestTaskQueryService.getPopularRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<ApiResponse<GetRequestTaskDetailDto.Response>> getRequestTaskDetail(
		@PathVariable("taskId") Long taskId
	) {
		GetRequestTaskDetailDto.Parameter parameter = GetRequestTaskDetailDtoConverter.toParameter(taskId);
		GetRequestTaskDetailDto.Response response = requestTaskQueryService.getRequestTaskDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
