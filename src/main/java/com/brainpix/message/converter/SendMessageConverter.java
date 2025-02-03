package com.brainpix.message.converter;

import com.brainpix.message.dto.SendMessageDto;
import com.brainpix.message.model.Message;

public class SendMessageConverter {

	public static SendMessageDto.Parameter toParameter(SendMessageDto.Request request, Long userId) {
		return SendMessageDto.Parameter.builder()
			.senderId(userId)
			.receiverId(request.getReceiverId())
			.title(request.getTitle())
			.content(request.getContent())
			.build();
	}

	public static Message toMessage(SendMessageDto.Parameter parameter) {
		return Message.builder()
			.senderId(parameter.getSenderId())
			.receiverId(parameter.getReceiverId())
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
