package com.brainpix.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.post.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

	private final S3Service s3Service;

	@GetMapping("/presigned-url")
	public ResponseEntity<String> generatePresignedUrl(@RequestParam String fileName) {
		String presignedUrl = s3Service.generatePresignedUrl(fileName);
		return ResponseEntity.ok(presignedUrl);
	}
}
