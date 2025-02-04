package com.brainpix.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.brainpix.api.exception.BrainPixException;
import com.brainpix.mail.dto.SendMailDto;
import com.brainpix.mail.service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailEventListener {

	private final MailService mailService;

	@KafkaListener(topics = "send-mail", groupId = "test-group")
	public void listenMailEvent(SendMailDto request) {
		try {
			mailService.sendMail(request);
		} catch (BrainPixException e) {
			log.error("failed to send mail to {}", request.getReceiverEmail());
		}
		log.info("received and send mail to {}", request.getReceiverEmail());
	}
}
