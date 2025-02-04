package com.brainpix.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/request-tasks")
@RequiredArgsConstructor
@Tag(name = "요청 과제 조회 API", description = "메인 페이지에서 요청과제를 조회하는 API입니다.")
public class RequestTaskQueryController {

	private final RequestTaskQueryService requestTaskQueryService;
	
	@Operation(summary = "요청 과제 전체 조회 [POST]", description = "json body로 요청 과제 타입(OPEN_IDEA, TECH_ZONE)과 검색 조건을 입력받고, 페이징을 위한 page, size는 쿼리 파라미터로 입력받아 전체 조회합니다.")
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail>>> getRequestTaskList(
		@RequestBody GetRequestTaskListDto.Request request, @PageableDefault(page = 0, size = 6) Pageable pageable) {
		GetRequestTaskListDto.Parameter parameter = GetRequestTaskListDtoConverter.toParameter(request, pageable);
		CommonPageResponse<GetRequestTaskListDto.RequestTaskDetail> response = requestTaskQueryService.getRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Operation(summary = "인기 요청 과제 조회 [GET]", description = "쿼리 파라미터로 요청 과제 타입(OPEN_IDEA, TECH_ZONE)과 page, size를 입력받아 저장순으로 조회합니다.")
	@GetMapping("/search/popular")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail>>> getPopularRequestTaskList(
		GetPopularRequestTaskListDto.Request request, @PageableDefault(page = 0, size = 3) Pageable pageable) {
		GetPopularRequestTaskListDto.Parameter parameter = GetPopularRequestTaskListDtoConverter.toParameter(request,
			pageable);
		CommonPageResponse<GetPopularRequestTaskListDto.RequestTaskDetail> response = requestTaskQueryService.getPopularRequestTaskList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "요청 과제 상세 조회 [GET]", description = "경로 변수로 요청과제 식별자 값을 입력받아 상세 조회합니다.")
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
