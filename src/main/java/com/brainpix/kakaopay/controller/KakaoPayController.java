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
import com.brainpix.kakaopay.service.KakaoPayFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/kakao-pay")
@RequiredArgsConstructor
@Tag(name = "카카오페이 아이디어 결제 API", description = "아이디어를 카카오페이로 결제하는 API 입니다.<br>결제 과정 : 결제 준비 API 호출 -> 리다이렉트된 사용자의 결제 진행 -> 결제 승인 API 호출")
public class KakaoPayController {

	private final KakaoPayFacadeService kakaoPayService;

	@Operation(summary = "결제 준비 API", description = "아이디어 식별자, 판매자 식별자, 상품 수량, 총 결제금액(vat 포함), vat를 json 본문으로 입력받아 카카오페이 쪽에 결제 정보를 생성합니다.<br>사용자를 결제 화면으로 이동시킬 수 있도록 리다이렉트 url과 주문 번호가 응답값으로 제공됩니다.<br>주문 번호는 이후 승인 단계에서 pgToken과 함께 전달해주시면 됩니다.")
	@PostMapping("/ready")
	public ResponseEntity<ApiResponse<KakaoPayReadyDto.Response>> kakaoPayReady(@RequestParam Long userId,
		@RequestBody KakaoPayReadyDto.Request request) {
		log.info("카카오페이 결제 준비 API 호출");
		KakaoPayReadyDto.Parameter parameter = KakaoPayReadyDtoConverter.toParameter(userId, request);
		KakaoPayReadyDto.Response response = kakaoPayService.kakaoPayReady(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Operation(summary = "결제 승인 API", description = "pgToken과 주문 번호를 json 본문으로 입력받아 최종 승인을 처리합니다.")
	@PostMapping("/approve")
	public ResponseEntity<ApiResponse<KakaoPayApproveDto.Response>> kakaoPayApprove(
		@RequestParam Long userId,
		@RequestBody KakaoPayApproveDto.Request request) {
		log.info("카카오페이 결제 최종 승인 API 호출");
		KakaoPayApproveDto.Parameter parameter = KakaoPayApproveDtoConverter.toParameter(userId, request);
		KakaoPayApproveDto.Response response = kakaoPayService.kakaoPayApprove(parameter);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
