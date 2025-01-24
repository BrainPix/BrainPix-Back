package com.brainpix.message.converter;

import lombok.Builder;
import lombok.Getter;

public class GetMessageCountDto {

	@Getter
	@Builder
	public static class Response {
		private Long readMessageCount;
		private Long unreadMessageCount;
	}
}
