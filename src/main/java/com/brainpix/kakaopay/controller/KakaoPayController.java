package com.brainpix.kakaopay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.kakaopay.converter.KakaoPayApproveDtoConverter;
import com.brainpix.kakaopay.converter.KakaoPayReadyDtoConverter;
import com.brainpix.kakaopay.dto.KakaoPayApproveDto;
import com.brainpix.kakaopay.dto.KakaoPayReadyDto;
import com.brainpix.kakaopay.service.KakaoPayService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/kakao-pay")
@RequiredArgsConstructor
@Slf4j
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;

	@PostMapping("/ready")
	public ResponseEntity<ApiResponse<KakaoPayReadyDto.Response>> kakaoPayReady(@RequestParam("userId") Long userId,
		@RequestBody KakaoPayReadyDto.Request request, HttpSession session) {
		log.info("카카오페이 결제 준비 API 호출");
		KakaoPayReadyDto.Parameter parameter = KakaoPayReadyDtoConverter.toParameter(userId, request);
		KakaoPayReadyDto.Response response = kakaoPayService.kakaoPayReady(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@PostMapping("/approve")
	public ResponseEntity<ApiResponse<KakaoPayApproveDto.Response>> kakaoPayApprove(
		@RequestParam("userId") Long userId,
		@RequestBody KakaoPayApproveDto.Request request) {
		log.info("카카오페이 결제 최종 승인 API 호출");
		KakaoPayApproveDto.Parameter parameter = KakaoPayApproveDtoConverter.toParameter(userId, request);
		KakaoPayApproveDto.Response response = kakaoPayService.kakaoPayApprove(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
