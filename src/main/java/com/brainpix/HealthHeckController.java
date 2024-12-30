package com.brainpix;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthHeckController {

	@GetMapping("/health")
	public String healthCheck() {
		return LocalDateTime.now().toString();
	}
}
