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
import com.brainpix.api.swagger.SwaggerPageable;
import com.brainpix.post.converter.ApplyCollaborationDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubDetailDtoConverter;
import com.brainpix.post.converter.GetCollaborationHubListDtoConverter;
import com.brainpix.post.dto.ApplyCollaborationDto;
import com.brainpix.post.dto.GetCollaborationHubDetailDto;
import com.brainpix.post.dto.GetCollaborationHubListDto;
import com.brainpix.post.service.CollaborationHubService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collaborations")
@RequiredArgsConstructor
@Tag(name = "협업 광장 검색 API", description = "협업 광장 관련 API")
public class CollaborationHubQueryController {

	private final CollaborationHubService collaborationHubService;

	@AllUser
	@Operation(summary = "협업 광장 신청 API", description = "경로 변수로 협업 게시글 식별자 값을 입력받고, json body로 지원 분야 식별자, 프로필 공개 여부, 추가 메시지를 입력받습니다.")
	@PostMapping("/{collaborationId}/apply")
	public ResponseEntity<ApiResponse<ApplyCollaborationDto.Response>> applyCollaboration(
		@PathVariable("collaborationId") Long collaborationId,
		@UserId Long userId,
		@RequestBody @Valid ApplyCollaborationDto.Request request) {
		ApplyCollaborationDto.Parameter parameter = ApplyCollaborationDtoConverter.toParameter(collaborationId, userId,
			request);
		ApplyCollaborationDto.Response response = collaborationHubService.applyCollaboration(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@SwaggerPageable
	@Operation(summary = "협업 광장 전체 조회 [POST]", description =
		"json body로 검색 조건을 입력받고, 쿼리 파라미터로 페이징을 위한 page, size를 입력받아 전체 조회합니다."
			+ "<br>category : ADVERTISING_PROMOTION, DESIGN, LESSON, MARKETING, DOCUMENT_WRITING, MEDIA_CONTENT,"
			+ " TRANSLATION_INTERPRETATION, TAX_LAW_LABOR, CUSTOM_PRODUCTION, STARTUP_BUSINESS, FOOD_BEVERAGE, IT_TECH, OTHERS"
			+ "<br>sortType : NEWEST, OLDEST, POPULAR")
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<CommonPageResponse<GetCollaborationHubListDto.CollaborationDetail>>> getCollaborationHubList(
		@UserId Long userId,
		@RequestBody GetCollaborationHubListDto.Request request,
		@PageableDefault(page = 0, size = 6) Pageable pageable) {
		GetCollaborationHubListDto.Parameter parameter = GetCollaborationHubListDtoConverter.toParameter(userId,
			request,
			pageable);
		CommonPageResponse<GetCollaborationHubListDto.CollaborationDetail> response = collaborationHubService.getCollaborationHubList(
			parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@AllUser
	@Operation(summary = "협업 광장 상세 조회 [GET]", description = "경로 변수로 협업 게시글 식별자 값을 입력받아 상세 조회합니다.")
	@GetMapping("/{collaborationId}")
	public ResponseEntity<ApiResponse<GetCollaborationHubDetailDto.Response>> getCollaborationHubDetail(
		@UserId Long userId,
		@PathVariable("collaborationId") Long collaborationId) {
		GetCollaborationHubDetailDto.Parameter parameter = GetCollaborationHubDetailDtoConverter.toParameter(userId,
			collaborationId);
		GetCollaborationHubDetailDto.Response response = collaborationHubService.getCollaborationHubDetail(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
