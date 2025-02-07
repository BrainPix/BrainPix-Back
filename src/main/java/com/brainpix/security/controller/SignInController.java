package com.brainpix.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.security.converter.SignInConverter;
import com.brainpix.security.dto.request.SignInRequest;
import com.brainpix.security.dto.response.SignInResponse;
import com.brainpix.security.tokenManger.TokenManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "로그인 API", description = "로그인 API입니다.")
@RestController
@RequestMapping("/users/login")
@RequiredArgsConstructor
public class SignInController {
	private final AuthenticationManager authenticationManager;
	private final TokenManager tokenManager;

	@Operation(summary = "로그인 API", description = "권한에 관계없이 1개의 API로 통합되어 있습니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<SignInResponse>> singIn(@RequestBody SignInRequest signInRequest) {
		Authentication authentication = authenticationManager.authenticate(
			SignInConverter.toAuthenticationToken(signInRequest));
		String jwt = tokenManager.writeAuthenticationToken(authentication);
		return ResponseEntity.ok(ApiResponse.success(new SignInResponse("Bearer " + jwt)));
	}
}
