package com.brainpix.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.post.service.FileUploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "PresignedUrl API", description = "PresignedUrl 발급 API")
public class FileUploadController {

	private final FileUploadService fileUploadService;

	//@AllUser
	@Operation(summary = "PresignedUrl 발급", description = "이미지, 파일 업로드를 위한 PresignedUrl을 발급합니다.")
	@GetMapping("/presigned-url")
	public ResponseEntity<String> generatePresignedUrl(@RequestParam String fileName,
		@RequestParam String contentType) {
		String presignedUrl = fileUploadService.generatePresignedUrl(fileName, contentType);
		return ResponseEntity.ok(presignedUrl);
	}
}
