package com.brainpix.joining.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.CommonPageResponse;
import com.brainpix.joining.dto.IdeaMarketPurchaseDto;
import com.brainpix.joining.service.SupportIdeaMarketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/supports/idea-market")
@RequiredArgsConstructor
public class SupportIdeaMarketController {

	private final SupportIdeaMarketService supportIdeaMarketService;

	@GetMapping("/purchases")
	public ResponseEntity<ApiResponse<CommonPageResponse<IdeaMarketPurchaseDto>>> getPurchases(
		@RequestParam Long userId,
		Pageable pageable
	) {
		CommonPageResponse<IdeaMarketPurchaseDto> response = supportIdeaMarketService.getMyPurchases(userId, pageable);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

}