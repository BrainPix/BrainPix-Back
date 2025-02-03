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
import com.brainpix.security.authorization.AllUser;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public-profile") // 기존 마이페이지 조회 API와 구별
@RequiredArgsConstructor
public class PublicProfileController {

	private final PublicProfileService publicProfileService;

	/**
	 * 개인 프로필 공개 조회
	 */
	@AllUser
	@Operation(summary = "개인 공개 프로필 조회", description = "특정 사용자에게 공개 개인 프로필을 조회합니다.")
	@GetMapping("/individual")
	public ResponseEntity<ApiResponse<IndividualProfileResponseDto>> getPublicIndividualProfile(
		@RequestParam Long userId) {
		IndividualProfileResponseDto profile = publicProfileService.getPublicIndividualProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	/**
	 * 기업 프로필 공개 조회
	 */
	@AllUser
	@Operation(summary = "기업 공개 프로필 조회", description = "특정 사용자에게 공개 기업 프로필을 조회합니다.")
	@GetMapping("/company")
	public ResponseEntity<ApiResponse<CompanyProfileResponseDto>> getPublicCompanyProfile(@RequestParam Long userId) {
		CompanyProfileResponseDto profile = publicProfileService.getPublicCompanyProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	@AllUser
	@Operation(summary = "사용자 게시글 조회", description = "특정 사용자가 작성한 공개 게시글을 조회합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<CommonPageResponse<PublicProfileResponseDto.PostPreviewDto>>> getPostsByUser(
		@RequestParam Long userId,
		@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
		CommonPageResponse<PublicProfileResponseDto.PostPreviewDto> pageResponse =
			CommonPageResponse.of(publicProfileService.getPostsByUser(userId, pageable));
		return ResponseEntity.ok(ApiResponse.success(pageResponse));
	}
}
