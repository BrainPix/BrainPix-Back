package com.brainpix.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.security.dto.EmailAuthCode;
import com.brainpix.security.dto.request.SendEmailNumberRequest;
import com.brainpix.security.service.EmailAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원가입시 사용되는 이메일 인증 API", description = "회원가입시 사용되는 이메일 인증 API입니다.")
@RestController
@RequestMapping("/users/login/email")
@RequiredArgsConstructor
public class EmailController {
	private final EmailAuthService emailAuthService;

	@Operation(summary = "입력 이메일로 인증 번호 전송", description = "입력한 이메일로 인증번호를 전송합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> postEmail(@RequestBody SendEmailNumberRequest sendEmailNumberRequest) {
		emailAuthService.sendEmailAuthCode(sendEmailNumberRequest);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "인증번호 확인", description = "입력한 인증번호가 맞는지 확인합니다.")
	@PostMapping("/auth")
	public ResponseEntity<ApiResponse<EmailAuthCode.Response>> checkAuthCode(
		@RequestBody EmailAuthCode.Request request) {
		EmailAuthCode.Response response = emailAuthService.checkEmailAuthCode(request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
