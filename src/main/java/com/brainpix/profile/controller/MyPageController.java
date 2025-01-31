package com.brainpix.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.profile.dto.MyPageResponseDto;
import com.brainpix.profile.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my-page")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping
	public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(@RequestParam Long userId) {
		MyPageResponseDto responseDto = myPageService.getMyPage(userId);
		return ResponseEntity.ok(ApiResponse.success(responseDto));
	}
}