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
import com.brainpix.security.authorization.AllUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
@Tag(name = "Message", description = "메시지 송수신 관련 API")
public class MessageController {

	private final MessageService messageService;

	@AllUser
	@GetMapping
	@Operation(summary = "메시지 다건 조회 API", description = "여러개의 메시지를 조회합니다.")
	public ResponseEntity<ApiResponse<GetMessageListDto.Response>> getMessageList(@RequestParam String status, Pageable pageable, @RequestParam Long userId) {

		GetMessageListDto.Parameter parameter = GetMessageListConverter.toParameter(userId, status, pageable);

		GetMessageListDto.Response data = messageService.getMessageList(parameter);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@AllUser
	@PostMapping
	@Operation(summary = "메시지 단건 전송 API", description = "메시지 하나를 전송합니다.")
	public ResponseEntity<ApiResponse<SendMessageDto.Response>> sendMessage(@RequestParam Long userId, @RequestBody SendMessageDto.Request request) {

		SendMessageDto.Parameter parameter = SendMessageConverter.toParameter(request, userId);

		SendMessageDto.Response data = messageService.sendMessage(parameter);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@AllUser
	@PatchMapping("/{messageId}")
	@Operation(summary = "메시지 읽음 처리 API", description = "메시지를 읽음 처리합니다.")
	public ResponseEntity<ApiResponse<Void>> readMessage(@PathVariable String messageId, @RequestParam Long userId) {

		messageService.readMessage(messageId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@GetMapping("/{messageId}")
	@Operation(summary = "메시지 단건 조회 API", description = "메시지 하나를 조회합니다.")
	public ResponseEntity<ApiResponse<GetMessageDto.Response>> getMessage(@PathVariable String messageId, @RequestParam Long userId) {

		GetMessageDto.Response data = messageService.getMessage(messageId, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@AllUser
	@GetMapping("/count")
	@Operation(summary = "받은 메시지 수 조회 API", description = "받은 메시지의 수를 조회합니다.")
	public ResponseEntity<ApiResponse<GetMessageCountDto.Response>> getMessageCount(@RequestParam Long userId) {

		GetMessageCountDto.Response data = messageService.getMessageCount(userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}
}
