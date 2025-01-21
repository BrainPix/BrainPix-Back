package com.brainpix.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaListenerService {

	@KafkaListener(topics = "test-message", groupId = "test-group")
	public void consume(String message) {
		log.info("Message produced: {}", message);
	}
}
