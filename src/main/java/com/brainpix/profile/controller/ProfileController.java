package com.brainpix.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.CompanyProfileUpdateDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileUpdateDto;
import com.brainpix.profile.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@GetMapping("/individual")
	public ResponseEntity<ApiResponse<IndividualProfileResponseDto>> getIndividualProfile(@RequestParam Long userId) {
		IndividualProfileResponseDto profile = profileService.getMyProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	@GetMapping("/company")
	public ResponseEntity<ApiResponse<CompanyProfileResponseDto>> getCompanyProfile(@RequestParam Long userId) {
		CompanyProfileResponseDto profile = profileService.getCompanyProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	@PutMapping("/individual/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateIndividualProfile(
		@PathVariable Long userId,
		@RequestBody IndividualProfileUpdateDto updateDto) {
		profileService.updateIndividualProfile(userId, updateDto);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@PutMapping("/company/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateCompanyProfile(
		@PathVariable Long userId,
		@RequestBody CompanyProfileUpdateDto updateDto) {
		profileService.updateCompanyProfile(userId, updateDto);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	// @PostMapping("/{userId}/upload-profile-image")
	// public ResponseEntity<ApiResponse<String>> uploadProfileImage(
	// 	@PathVariable Long userId,
	// 	@RequestParam("file") MultipartFile file) {
	// 	// TODO: 파일을 스토리지에 저장하고 경로를 가져오는 로직 필요 (AWS S3 등 사용)
	// 	String imagePath = "/path/to/uploaded/image.jpg"; // 예시
	// 	String savedPath = profileService.uploadProfileImage(userId, imagePath);
	// 	return ResponseEntity.ok(ApiResponse.success(savedPath));
	// }

}
