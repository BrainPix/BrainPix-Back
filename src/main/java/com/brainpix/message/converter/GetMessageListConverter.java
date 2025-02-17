package com.brainpix.message.converter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.code.error.MessageErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.message.dto.GetMessageListDto;
import com.brainpix.message.dto.MessageSearchType;
import com.brainpix.message.dto.MessageType;
import com.brainpix.message.model.Message;

public class GetMessageListConverter {
	public static GetMessageListDto.Parameter toParameter(Long userId, String searchType, Pageable pageable) {
		MessageSearchType messageSearchType;
		try {
			messageSearchType = MessageSearchType.valueOf(searchType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BrainPixException(MessageErrorCode.INVALID_SEARCH_TYPE);
		}
		return GetMessageListDto.Parameter.builder()
			.userId(userId)
			.searchType(messageSearchType)
			.pageable(pageable)
			.build();
	}

	public static GetMessageListDto.Response toResponse(Page<Message> messages, Map<Long, String> userIdToNameMap,
		Long userId) {
		List<Message> messageList = messages.getContent();
		List<GetMessageListDto.MessageDetail> messageDetailList = messageList.stream()
			.map(message -> toMessageDetail(message, userIdToNameMap, userId))
			.toList();

		return GetMessageListDto.Response.builder()
			.messageDetailList(messageDetailList)
			.hasNext(messages.hasNext())
			.currentPage(messages.getNumber())
			.build();
	}

	public static GetMessageListDto.MessageDetail toMessageDetail(Message message, Map<Long, String> userIdToNameMap,
		Long userId) {
		return GetMessageListDto.MessageDetail.builder()
			.messageId(message.getId())
			.title(message.getTitle())
			.senderNickname(userIdToNameMap.get(message.getSenderId()))
			.receiverNickname(userIdToNameMap.get(message.getReceiverId()))
			.sendDate(message.getCreatedAt().toLocalDate())
			.isRead(message.getIsRead())
			.messageType(Objects.equals(message.getReceiverId(), userId) ? MessageType.RECEIVED : MessageType.SEND)
			.build();
	}
}
