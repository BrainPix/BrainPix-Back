package com.brainpix.message.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.api.ApiResponse;
import com.brainpix.message.dto.GetMessageCountDto;
import com.brainpix.message.converter.GetMessageListConverter;
import com.brainpix.message.converter.SendMessageConverter;
import com.brainpix.message.dto.GetMessageDto;
import com.brainpix.message.dto.GetMessageListDto;
import com.brainpix.message.dto.SendMessageDto;
import com.brainpix.message.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

	private final MessageService messageService;

	@GetMapping
	public ResponseEntity<?> getMessageList(@RequestParam String status, Pageable pageable, @RequestParam Long userId) {

		GetMessageListDto.Parameter parameter = GetMessageListConverter.toParameter(userId, status, pageable);

		GetMessageListDto.Response data = messageService.getMessageList(parameter);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@PostMapping
	public ResponseEntity<?> sendMessage(@RequestParam Long userId, @RequestBody SendMessageDto.Request request) {

		SendMessageDto.Parameter parameter = SendMessageConverter.toParameter(request, userId);

		SendMessageDto.Response data = messageService.sendMessage(parameter);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@PatchMapping("/{messageId}")
	public ResponseEntity<?> readMessage(@PathVariable String messageId, @RequestParam Long userId) {

		messageService.readMessage(messageId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping("/{messageId}")
	public ResponseEntity<?> getMessage(@PathVariable String messageId, @RequestParam Long userId) {

		GetMessageDto.Response data = messageService.getMessage(messageId, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@GetMapping("/count")
	public ResponseEntity<?> getMessageCount(@RequestParam Long userId) {

		GetMessageCountDto.Response data = messageService.getMessageCount(userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}
}
