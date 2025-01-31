package com.brainpix.message.converter;

import com.brainpix.message.dto.GetMessageDto;
import com.brainpix.message.model.Message;
import com.brainpix.user.entity.User;

public class GetMessageConverter {

	public static GetMessageDto.Response toResponse(Message message, User receiver, User sender) {
		return GetMessageDto.Response.builder()
			.id(message.getId())
			.senderNickname(sender.getNickName())
			.receiverNickname(receiver.getNickName())
			.title(message.getTitle())
			.content(message.getContent())
			.sendTime(message.getCreatedAt())
			.build();
	}
}
