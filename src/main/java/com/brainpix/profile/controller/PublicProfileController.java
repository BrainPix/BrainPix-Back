package com.brainpix.profile.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.swagger.SwaggerPageable;
import com.brainpix.profile.dto.CompanyProfileResponseDto;
import com.brainpix.profile.dto.IndividualProfileResponseDto;
import com.brainpix.profile.dto.PublicProfileResponseDto;
import com.brainpix.profile.service.PublicProfileService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public-profile") // 기존 마이페이지 조회 API와 구별
@Tag(name = "상대방 프로필 조회 API", description = "상대방 계정의 프로필을 조회 API 입니다.")
@RequiredArgsConstructor
public class PublicProfileController {

	private final PublicProfileService publicProfileService;

	/**
	 * 개인 프로필 공개 조회
	 */
	@AllUser
	@Operation(summary = "개인 공개 프로필 조회", description = "특정 사용자에게 공개 개인 프로필을 조회합니다.")
	@GetMapping("/individual/{userId}")
	public ResponseEntity<ApiResponse<IndividualProfileResponseDto>> getPublicIndividualProfile(
		@PathVariable Long userId) {
		IndividualProfileResponseDto profile = publicProfileService.getPublicIndividualProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	/**
	 * 기업 프로필 공개 조회
	 */
	@AllUser
	@Operation(summary = "기업 공개 프로필 조회", description = "특정 사용자에게 공개 기업 프로필을 조회합니다.")
	@GetMapping("/company/{userId}")
	public ResponseEntity<ApiResponse<CompanyProfileResponseDto>> getPublicCompanyProfile(@PathVariable Long userId) {
		CompanyProfileResponseDto profile = publicProfileService.getPublicCompanyProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(profile));
	}

	@AllUser
	@Operation(summary = "사용자 게시글 조회", description = "특정 사용자가 작성한 공개 게시글을 조회합니다.")
	@GetMapping("/{userId}")
	@SwaggerPageable
	public ResponseEntity<ApiResponse<CommonPageResponse<PublicProfileResponseDto.PostPreviewDto>>> getPostsByUser(
		@PathVariable Long userId,
		@UserId Long currentUserId,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		CommonPageResponse<PublicProfileResponseDto.PostPreviewDto> pageResponse =
			CommonPageResponse.of(publicProfileService.getPostsByUser(userId, currentUserId, pageable));
		return ResponseEntity.ok(ApiResponse.success(pageResponse));
	}
}
