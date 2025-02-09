package com.brainpix.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.CompanyProfileUpdateDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileUpdateDto;
import com.brainpix.profile.service.ProfileService;
import com.brainpix.security.authorization.Company;
import com.brainpix.security.authorization.Individual;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@Tag(name = "본인 프로필 조회/수정 API", description = "개인/기업 본인시 소유한 계정의 프로필을 조회 및 수정 API 입니다.")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@Operation(summary = "개인 사용자 프로필 조회", description = "현재 로그인한 개인 사용자의 프로필을 조회합니다")
	@Individual
	@GetMapping("/individual")
	public ResponseEntity<ApiResponse<IndividualProfileResponseDto>> getIndividualProfile(@UserId Long userId) {
		IndividualProfileResponseDto profile = profileService.getIndividualProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	@Operation(summary = "기업 사용자 프로필 조회", description = "현재 로그인한 기업 사용자의 프로필을 조회합니다.")
	@Company
	@GetMapping("/company")
	public ResponseEntity<ApiResponse<CompanyProfileResponseDto>> getCompanyProfile(@UserId Long userId) {
		CompanyProfileResponseDto profile = profileService.getCompanyProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	@Operation(summary = "개인 사용자 프로필 수정", description = "현재 로그인한 개인 사용자의 프로필 정보를 업데이트합니다.")
	@Individual
	@PutMapping("/individual/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateIndividualProfile(
		@UserId Long userId,
		@RequestBody IndividualProfileUpdateDto updateDto) {
		profileService.updateIndividualProfile(userId, updateDto);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@Operation(summary = "기업 사용자 프로필 수정", description = "현재 로그인한 기업 사용자의 프로필 정보를 업데이트합니다.")
	@Company
	@PutMapping("/company/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateCompanyProfile(
		@UserId Long userId,
		@RequestBody CompanyProfileUpdateDto updateDto) {
		profileService.updateCompanyProfile(userId, updateDto);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	/*@Operation(summary = "프로필 이미지 업로드", description = "현재 로그인한 사용자의 프로필 이미지를 업로드합니다.")
	@AllUser
	@PostMapping("/{userId}/upload-profile-image")
	public ResponseEntity<ApiResponse<String>> uploadProfileImage(
		@UserId Long userId,
		@RequestParam("file") MultipartFile file) {
		// TODO: 파일을 스토리지에 저장하고 경로를 가져오는 로직 필요 (AWS S3 등 사용)
		String imagePath = "/path/to/uploaded/image.jpg"; // 예시
		String savedPath = profileService.uploadProfileImage(userId, imagePath);
		return ResponseEntity.ok(ApiResponse.success(savedPath));
	}*/
}
