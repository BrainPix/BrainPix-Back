package com.brainpix.security.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.AuthorityErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.kafka.service.MailEventService;
import com.brainpix.mail.dto.SendMailDto;
import com.brainpix.security.converter.EmailAuthCodeConverter;
import com.brainpix.security.dto.EmailAuthCode;
import com.brainpix.security.dto.request.SendEmailNumberRequest;
import com.brainpix.security.tokenManger.TokenManager;
import com.brainpix.user.entity.EmailAuth;
import com.brainpix.user.repository.EmailAuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

	private final EmailAuthRepository emailAuthRepository;
	private final MailEventService mailEventService;
	private final TokenManager tokenManager;

	@Transactional
	public void sendEmailAuthCode(SendEmailNumberRequest sendEmailNumberRequest) {
		SendMailDto sendMailDto = SendMailDto.builder()
			.receiverEmail(sendEmailNumberRequest.getEmail())
			.title("BrainPix 회원가입 인증 코드")
			.signupCode(String.valueOf(generateRandomNumber()))
			.build();
		EmailAuth emailAuth = EmailAuth.builder()
			.email(sendEmailNumberRequest.getEmail())
			.authCode(sendMailDto.getSignupCode())
			.build();

		emailAuthRepository.save(emailAuth);
		mailEventService.sendMailEvent(sendMailDto);
	}

	@Transactional
	public EmailAuthCode.Response checkEmailAuthCode(EmailAuthCode.Request emailAuthCode) {
		EmailAuth emailAuth = emailAuthRepository.findFirstByEmailOrderByCreatedAtDesc(emailAuthCode.getEmail())
			.orElseThrow(() -> new BrainPixException(AuthorityErrorCode.EMAIL_AUTH_CODE_NOT_FOUND));

		emailAuth.checkAuthTime();
		if (emailAuth.getAuthCode().equals(emailAuthCode.getAuthCode())) {
			emailAuthRepository.delete(emailAuth);
			String token = tokenManager.writeEmailAuthCodeToken(emailAuthCode.getEmail(), emailAuthCode.getAuthCode());
			return EmailAuthCodeConverter.toResponse(token);
		} else {
			throw new BrainPixException(AuthorityErrorCode.EMAIL_AUTH_CODE_NOT_MATCH);
		}
	}

	private int generateRandomNumber() {
		SecureRandom random = new SecureRandom();
		return 100000 + random.nextInt(900000);
	}
}
