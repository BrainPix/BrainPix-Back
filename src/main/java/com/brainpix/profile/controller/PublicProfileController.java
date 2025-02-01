package com.brainpix.profile.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.dto.PublicProfileResponseDto;
import com.brainpix.profile.service.PublicProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public-profile") // 기존 마이페이지 조회 API와 구별
@RequiredArgsConstructor
public class PublicProfileController {

	private final PublicProfileService publicProfileService;

	/**
	 * 개인 프로필 공개 조회
	 */
	@GetMapping("/individual")
	public ResponseEntity<ApiResponse<IndividualProfileResponseDto>> getPublicIndividualProfile(
		@RequestParam Long userId) {
		IndividualProfileResponseDto profile = publicProfileService.getPublicIndividualProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	/**
	 * 기업 프로필 공개 조회
	 */
	@GetMapping("/company")
	public ResponseEntity<ApiResponse<CompanyProfileResponseDto>> getPublicCompanyProfile(@RequestParam Long userId) {
		CompanyProfileResponseDto profile = publicProfileService.getPublicCompanyProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<PublicProfileResponseDto.PostPreviewDto>>> getPostsByUser(
		@RequestParam Long userId,
		@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
		CommonPageResponse<PublicProfileResponseDto.PostPreviewDto> pageResponse =
			CommonPageResponse.of(publicProfileService.getPostsByUser(userId, pageable));
		return ResponseEntity.ok(ApiResponse.success(pageResponse));
	}
}
