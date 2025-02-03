package com.brainpix.profile.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.profile.dto.MyPageResponseDto;
import com.brainpix.profile.service.MyPageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my-page")
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
	@GetMapping("/ideas")
	public ResponseEntity<ApiResponse<CommonPageResponse<String>>> getMyIdeas(
		@UserId Long userId,
		Pageable pageable) {
		return ResponseEntity.ok(
			ApiResponse.success(CommonPageResponse.of(myPageService.getMyIdeas(userId, pageable))));
	}
}