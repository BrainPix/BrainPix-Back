package com.brainpix.message.converter;

import com.brainpix.message.dto.SendMessageDto;
import com.brainpix.message.model.Message;

public class SendMessageConverter {

	public static SendMessageDto.Parameter toParameter(SendMessageDto.Request request, Long userId) {
		return SendMessageDto.Parameter.builder()
			.senderId(userId)
			.receiverNickname(request.getReceiverNickname())
			.title(request.getTitle())
			.content(request.getContent())
			.build();
	}

	public static Message toMessage(SendMessageDto.Parameter parameter, Long receiverId) {
		return Message.builder()
			.senderId(parameter.getSenderId())
			.receiverId(receiverId)
			.title(parameter.getTitle())
			.content(parameter.getContent())
			.build();
	}

	public static SendMessageDto.Response toResponse(Message message) {
		return SendMessageDto.Response.builder()
			.messageId(message.getId())
			.build();
	}
}
