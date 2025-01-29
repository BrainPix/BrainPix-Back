package com.brainpix.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.profile.dto.MyProfileResponseDto;
import com.brainpix.profile.service.MyProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my-profile")
@RequiredArgsConstructor
public class MyProfileController {

	private final MyProfileService myProfileService;

	@GetMapping
	public ResponseEntity<ApiResponse<MyProfileResponseDto>> getMyProfile(@RequestParam Long userId) {
		MyProfileResponseDto profile = myProfileService.getMyProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}
}