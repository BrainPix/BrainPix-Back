package com.brainpix.mail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.kafka.service.MailEventService;
import com.brainpix.mail.dto.SendMailDto;
import com.brainpix.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

	private final MailService mailService;
	private final MailEventService mailEventService;

	@PostMapping("/test")
	public ResponseEntity<?> sendMail(@RequestParam String to, @RequestParam String subject) {
		SendMailDto request = SendMailDto.builder()
			.receiverEmail(to)
			.title(subject)
			.signupCode("test")
			.build();

		mailService.sendMail(request);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@PostMapping("/kafka/test")
	public ResponseEntity<?> sendMailKafka(@RequestParam String to, @RequestParam String subject) {
		SendMailDto request = SendMailDto.builder()
			.receiverEmail(to)
			.title(subject)
			.signupCode("test")
			.build();

		mailEventService.sendMailEvent(request);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
