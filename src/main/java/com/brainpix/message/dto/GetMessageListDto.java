package com.brainpix.message.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;

public class GetMessageListDto {

	@Getter
	@Builder
	public static class Parameter {
		private Long userId;
		private Pageable pageable;
		private MessageSearchType searchType;
	}

	@Getter
	@Builder
	public static class Response {
		private List<MessageDetail> messageDetailList;
		private Boolean hasNext;
	}

	@Getter
	@Builder
	public static class MessageDetail {
		private String messageId;
		private String title;
		private String senderNickname;
		private String receiverNickname;
		private LocalDate sendDate;
		private Boolean isRead;
	}
}
