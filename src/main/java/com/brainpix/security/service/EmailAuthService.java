package com.brainpix.security.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.kafka.service.MailEventService;
import com.brainpix.mail.dto.SendMailDto;
import com.brainpix.security.dto.request.SendEmailNumberRequest;
import com.brainpix.user.entity.EmailAuth;
import com.brainpix.user.repository.EmailAuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

	private final EmailAuthRepository emailAuthRepository;
	private final MailEventService mailEventService;

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

	private int generateRandomNumber() {
		SecureRandom random = new SecureRandom();
		return 100000 + random.nextInt(900000);
	}
}
