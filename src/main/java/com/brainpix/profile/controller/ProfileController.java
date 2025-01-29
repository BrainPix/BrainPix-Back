package com.brainpix.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brainpix.api.ApiResponse;
import com.brainpix.profile.dto.ProfileUpdateDto;
import com.brainpix.profile.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateProfile(
		@PathVariable Long userId,
		@RequestBody ProfileUpdateDto updateDto) {
		profileService.updateProfile(userId, updateDto);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@PostMapping("/{userId}/upload-profile-image")
	public ResponseEntity<ApiResponse<String>> uploadProfileImage(
		@PathVariable Long userId,
		@RequestParam("file") MultipartFile file) {
		// TODO: 파일을 스토리지에 저장하고 경로를 가져오는 로직 필요 (AWS S3 등 사용)
		String imagePath = "/path/to/uploaded/image.jpg"; // 예시
		String savedPath = profileService.uploadProfileImage(userId, imagePath);
		return ResponseEntity.ok(ApiResponse.success(savedPath));
	}
}
