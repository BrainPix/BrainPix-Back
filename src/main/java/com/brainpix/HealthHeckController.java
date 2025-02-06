package com.brainpix;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "서버 확인용 API", description = "서버 확인용 API입니다.")
@RestController
public class HealthHeckController {

	@Operation(summary = "서버 상태 확인", description = "서버 상태를 확인합니다.")
	@GetMapping("/health")
	public String healthCheck() {
		return LocalDateTime.now().toString();
	}
}
