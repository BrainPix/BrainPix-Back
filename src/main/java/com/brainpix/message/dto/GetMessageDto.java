package com.brainpix.message.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

public class GetMessageDto {

	@Getter
	@Builder
	public static class Response {
		private String id;
		private String senderNickname;
		private String receiverNickname;
		private String title;
		private String content;
		private LocalDateTime sendTime;
	}
}
