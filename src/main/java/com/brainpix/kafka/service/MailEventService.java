package com.brainpix.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.brainpix.mail.dto.SendMailDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailEventService {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendMailEvent(SendMailDto request) {
		kafkaTemplate.send("send-mail", request);
		log.info("sending mail event to {}", request.getReceiverEmail());
	}
}
