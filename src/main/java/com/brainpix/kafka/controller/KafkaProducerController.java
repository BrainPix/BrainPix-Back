package com.brainpix.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.kafka.service.KafkaProducerService;
import com.brainpix.api.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaProducerController {
	private final KafkaProducerService kafkaProducerService;

	@PostMapping("/test")
	public ResponseEntity<?> sendMessage(@RequestParam String message) {
		kafkaProducerService.sendTestMessage(message);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
	
}
