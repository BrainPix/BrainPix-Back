package com.brainpix.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.post.converter.ApplyRequestTaskDtoConverter;
import com.brainpix.post.dto.ApplyRequestTaskDto;
import com.brainpix.post.dto.PostApiResponseDto;
import com.brainpix.post.dto.RequestTaskCreateDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.service.RequestTaskCommandService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/request-tasks")
@RequiredArgsConstructor
@Tag(name = "RequestTask API", description = "요청 과제 관련 API")
public class RequestTaskCommandController {

	private final RequestTaskCommandService requestTaskCommandService;

	@AllUser
	@Operation(summary = "요청 과제 글 생성", description = "요청 과제 글 내용, 모집 정보를 포함하여 요청 과제 게시글을 생성합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<PostApiResponseDto>> createRequestTask(@UserId Long userId,
		@Valid @RequestBody RequestTaskCreateDto createDto) {
		Long taskId = requestTaskCommandService.createRequestTask(userId, createDto); // 컨버터행
		return ResponseEntity.ok(ApiResponse.success(new PostApiResponseDto("taskId", taskId)));
	}

	@AllUser
	@Operation(summary = "요청 과제 글 수정", description = "요청 과제 게시글 내용을 수정합니다, 모집 분야는 수정할 수 없습니다.")
	@PutMapping("/{taskId}")
	public ResponseEntity<ApiResponse<Void>> updateRequestTask(@PathVariable("taskId") Long taskid,
		@UserId Long userId, @Valid @RequestBody RequestTaskUpdateDto updateDto) {
		requestTaskCommandService.updateRequestTask(taskid, userId, updateDto);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@Operation(summary = "요청 과제 글 삭제", description = "요청 과제 게시글을 삭제합니다.")
	@DeleteMapping("/{taskId}")
	public ResponseEntity<ApiResponse<Void>> deleteRequestTask(@PathVariable("taskId") Long taskid,
		@UserId Long userId) {
		requestTaskCommandService.deleteRequestTask(taskid, userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@Operation(summary = "요청 과제 신청 API", description = "요청 과제에서 특정 모집 분야에 지원하는 API입니다.<br>경로 변수로 요청 과제 식별자를 입력받고, json body로 모집 분야 식별자 값, 프로필 공개 여부, 추가 메시지를 입력받습니다.")
	@PostMapping("/{taskId}/apply")
	public ResponseEntity<ApiResponse<ApplyRequestTaskDto.Response>> applyRequestTask(
		@PathVariable("taskId") Long taskId,
		@UserId Long userId,
		@Valid @RequestBody ApplyRequestTaskDto.Request request
	) {
		ApplyRequestTaskDto.Parameter parameter = ApplyRequestTaskDtoConverter.toParameter(taskId, userId, request);
		ApplyRequestTaskDto.Response response = requestTaskCommandService.applyRequestTask(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
