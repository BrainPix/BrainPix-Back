package com.brainpix.message.dto;

import lombok.Builder;
import lombok.Getter;

public class SendMessageDto {

	@Getter
	@Builder
	public static class Request {
		private String receiverNickname;
		private String title;
		private String content;
	}

	@Getter
	@Builder
	public static class Parameter {
		private Long senderId;
		private String receiverNickname;
		private String title;
		private String content;
	}

	@Getter
	@Builder
	public static class Response {
		private String messageId;
	}
}
