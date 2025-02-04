package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/request-tasks")
@RequiredArgsConstructor
public class RequestTaskQueryController {

	private final RequestTaskQueryService requestTaskQueryService;

	@AllUser
	@Operation(summary = "요청 과제 전체 조회", description = "쿼리 파라미터로 요청 과제 타입(OPEN_IDEA, TECH_ZONE)과 검색 조건, page, size를 입력받아 전체 조회합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail>>> getRequestTaskList(
		GetRequestTaskListDto.Request request, @PageableDefault(page = 0, size = 6) Pageable pageable) {
		GetRequestTaskListDto.Parameter parameter = GetRequestTaskListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail> response = requestTaskQueryService.getRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Operation(summary = "인기 요청 과제 조회", description = "쿼리 파라미터로 요청 과제 타입(OPEN_IDEA, TECH_ZONE)과 page, size를 입력받아 저장순으로 조회합니다.")
	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail>>> getPopularRequestTaskList(
		GetPopularRequestTaskListDto.Request request, @PageableDefault(page = 0, size = 3) Pageable pageable) {
		GetPopularRequestTaskListDto.Parameter parameter = GetPopularRequestTaskListDtoConverter.toParameter(request,
			pageable);
		CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail> response = requestTaskQueryService.getPopularRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "요청 과제 상세 조회", description = "경로 변수로 요청과제 식별자 값을 입력받아 상세 조회합니다.")
	@GetMapping("/{taskId}")
	public ResponseEntity<ApiResponse<GetRequestTaskDetailDto.Response>> getRequestTaskDetail(
		@UserId Long userId,
		@PathVariable("taskId") Long taskId
	) {
		GetRequestTaskDetailDto.Parameter parameter = GetRequestTaskDetailDtoConverter.toParameter(taskId, userId);
		GetRequestTaskDetailDto.Response response = requestTaskQueryService.getRequestTaskDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
