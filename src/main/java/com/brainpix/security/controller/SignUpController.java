package com.brainpix.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.security.dto.request.SignUpRequest;
import com.brainpix.security.service.CompanySignUpService;
import com.brainpix.security.service.IndividualSignUpService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원가입 API", description = "회원가입 API입니다.")
@RestController
@RequestMapping("/users/signup")
@RequiredArgsConstructor
public class SignUpController {

	private final IndividualSignUpService individualSignUpService;
	private final CompanySignUpService companySignUpService;

	@Operation(summary = "개인 회원가입 API", description = "개인 회원가입 API입니다.")
	@PostMapping("/personal")
	public ResponseEntity<ApiResponse<Void>> signUpPersonal(
		@RequestBody SignUpRequest.PersonalSignUpRequest personalSignUpRequest) {
		individualSignUpService.signUpUser(personalSignUpRequest);
		return ResponseEntity.ok()
			.body(ApiResponse.createdWithNoData());
	}

	@Operation(summary = "기업 회원가입 API", description = "기업 회원가입 API입니다.")
	@PostMapping("/company")
	public ResponseEntity<ApiResponse<Void>> signUpCompany(
		@RequestBody SignUpRequest.CompanySignUpRequest companySignUpRequest) {
		companySignUpService.signUpUser(companySignUpRequest);
		return ResponseEntity.ok()
			.body(ApiResponse.createdWithNoData());
	}

	@Operation(summary = "아이디 중복 확인 API", description = "아이디 중복 확인 API입니다.")
	@GetMapping("/duplicate/id")
	public ResponseEntity<ApiResponse<Void>> checkDuplicateId(@RequestParam("id") String id) {
		individualSignUpService.checkDuplicated(id);
		return ResponseEntity.ok()
			.body(ApiResponse.successWithNoData());
	}

	@Operation(summary = "닉네임 중복 확인 API", description = "닉네임 중복 확인 API입니다. 기업은 회사명을 닉네임으로 사용합니다")
	@GetMapping("/duplicate/nickname")
	public ResponseEntity<ApiResponse<Void>> checkDuplicateNickName(@RequestParam("nickName") String nickName) {
		individualSignUpService.checkDuplicatedNickName(nickName);
		return ResponseEntity.ok()
			.body(ApiResponse.successWithNoData());
	}
}
