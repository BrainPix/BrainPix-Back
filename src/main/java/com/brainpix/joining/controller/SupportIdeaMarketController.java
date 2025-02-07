package com.brainpix.joining.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.swagger.SwaggerPageable;
import com.brainpix.joining.dto.IdeaMarketPurchaseDto;
import com.brainpix.joining.service.SupportIdeaMarketService;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supports/idea-market")
@RequiredArgsConstructor
@Tag(name = "지원 현황 -아이디어마켓 조회 API", description = "아이디어 마켓 구매내역을 조회합니다.")
public class SupportIdeaMarketController {

	private final SupportIdeaMarketService supportIdeaMarketService;

	@Operation(summary = "구매 내역 조회", description = "현재 로그인한 사용자의 아이디어 마켓 구매 내역을 조회합니다.")
	@AllUser
	@GetMapping("/purchases")
	@SwaggerPageable
	public ResponseEntity<ApiResponse<CommonPageResponse<IdeaMarketPurchaseDto>>> getPurchases(
		@UserId Long userId,
		Pageable pageable
	) {
		CommonPageResponse<IdeaMarketPurchaseDto> response = supportIdeaMarketService.getMyPurchases(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

}