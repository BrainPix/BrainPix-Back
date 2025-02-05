package com.brainpix.profile.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.swagger.SwaggerPageable;
import com.brainpix.post.dto.MyDefaultPageIdeaListDto;
import com.brainpix.profile.dto.MyPageResponseDto;
import com.brainpix.profile.service.MyPageService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my-page")
@Tag(name = "마이페이지 기본 화면 조회", description = "마이페이지 기본 화면 조회 관련 API")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	@AllUser
	@Operation(summary = "마이페이지 조회", description = "특정 사용자의 마이페이지 정보를 조회합니다.('내 아이디어','최근소식'은 다른 api로 대체합니다)")
	@GetMapping
	public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(@UserId Long userId) {
		MyPageResponseDto responseDto = myPageService.getMyPage(userId);
		return ResponseEntity.ok(ApiResponse.success(responseDto));
	}

	@AllUser
	@Operation(summary = "'내 아이디어' 조회", description = "특정 사용자가 작성한 아이디어 목록을 페이징 처리하여 조회합니다")
	@SwaggerPageable
	@GetMapping("/ideas")
	public ResponseEntity<ApiResponse<CommonPageResponse<MyDefaultPageIdeaListDto>>> getMyIdeas(
		@UserId Long userId,
		Pageable pageable) {
		return ResponseEntity.ok(
			ApiResponse.success(CommonPageResponse.of(myPageService.getMyIdeas(userId, pageable))));
	}
}