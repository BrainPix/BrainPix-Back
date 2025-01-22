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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/signup")
@RequiredArgsConstructor
public class SignUpController {

	private final IndividualSignUpService individualSignUpService;
	private final CompanySignUpService companySignUpService;

	@PostMapping("/personal")
	public ResponseEntity<ApiResponse<Void>> signUpPersonal(
		@RequestBody SignUpRequest.PersonalSignUpRequest personalSignUpRequest) {
		individualSignUpService.signUpUser(personalSignUpRequest);
		return ResponseEntity.ok()
			.body(ApiResponse.createdWithNoData());
	}

	@PostMapping("/company")
	public ResponseEntity<ApiResponse<Void>> signUpCompany(
		@RequestBody SignUpRequest.CompanySignUpRequest companySignUpRequest) {
		companySignUpService.signUpUser(companySignUpRequest);
		return ResponseEntity.ok()
			.body(ApiResponse.createdWithNoData());
	}

	@GetMapping("/duplicate/id")
	public ResponseEntity<ApiResponse<Void>> checkDuplicateId(@RequestParam("id") String id) {
		individualSignUpService.isDuplicated(id);
		return ResponseEntity.ok()
			.body(ApiResponse.successWithNoData());
	}

	@GetMapping("/duplicate/nickname")
	public ResponseEntity<ApiResponse<Void>> checkDuplicateNickName(@RequestParam("nickName") String nickName) {
		individualSignUpService.isDuplicatedNickName(nickName);
		return ResponseEntity.ok()
			.body(ApiResponse.successWithNoData());
	}
}
