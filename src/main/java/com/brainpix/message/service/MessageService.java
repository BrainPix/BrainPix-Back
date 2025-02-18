package com.brainpix.message.service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.ErrorCode;
import com.brainpix.api.code.error.MessageErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.message.converter.GetMessageConverter;
import com.brainpix.message.dto.GetMessageCountDto;
import com.brainpix.message.converter.GetMessageListConverter;
import com.brainpix.message.converter.SendMessageConverter;
import com.brainpix.message.dto.GetMessageDto;
import com.brainpix.message.dto.GetMessageListDto;
import com.brainpix.message.dto.MessageSearchType;
import com.brainpix.message.dto.SendMessageDto;
import com.brainpix.message.model.Message;
import com.brainpix.message.repository.MessageRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public GetMessageDto.Response getMessage(String messageId, Long userId) {
		Message message = messageRepository.findById(messageId)
			.orElseThrow(() -> new BrainPixException(MessageErrorCode.MESSAGE_NOT_FOUND));

		User sender = getUser(message.getSenderId(), MessageErrorCode.SENDER_NOT_FOUND);
		User receiver = getUser(message.getReceiverId(), MessageErrorCode.RECEIVER_NOT_FOUND);

		if (!Objects.equals(userId, receiver.getId())) {
			throw new BrainPixException(MessageErrorCode.NOT_YOUR_MESSAGE);
		}

		return GetMessageConverter.toResponse(message, receiver, sender);
	}

	@Transactional(readOnly = true)
	public GetMessageListDto.Response getMessageList(GetMessageListDto.Parameter parameter) {
		Pageable pageable = parameter.getPageable();
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
			Sort.by("createdAt").descending());

		Page<Message> messages = getMessageListBySearchType(parameter.getUserId(), parameter.getSearchType(),
			pageRequest);

		// 수신자와 송신자의 id를 모두 가져옴
		Set<Long> senderIdSet = messages.stream().map(Message::getSenderId).collect(Collectors.toSet());
		Set<Long> receiverIdSet = messages.stream().map(Message::getReceiverId).collect(Collectors.toSet());
		Set<Long> senderAndReceiverIdSet = Stream.concat(senderIdSet.stream(), receiverIdSet.stream())
			.collect(Collectors.toSet());

		// in 쿼리로 한번에 가져온 후 Map 으로 변환
		Map<Long, String> userIdAndNickNameMap = userRepository.findAllByIdIn(senderAndReceiverIdSet)
			.stream()
			.collect(Collectors.toMap(User::getId, User::getNickName));

		return GetMessageListConverter.toResponse(messages, userIdAndNickNameMap, parameter.getUserId());
	}

	@Transactional(readOnly = true)
	public SendMessageDto.Response sendMessage(SendMessageDto.Parameter parameter) {
		validateUserExists(parameter.getSenderId(), MessageErrorCode.SENDER_NOT_FOUND);
		User receiver = userRepository.findByNickName(parameter.getReceiverNickname())
			.orElseThrow(() -> new BrainPixException(MessageErrorCode.RECEIVER_NOT_FOUND));

		Message message = SendMessageConverter.toMessage(parameter, receiver.getId());

		messageRepository.save(message);

		return SendMessageConverter.toResponse(message);
	}

	@Transactional(readOnly = true)
	public GetMessageCountDto.Response getMessageCount(Long userId) {
		validateUserExists(userId, MessageErrorCode.RECEIVER_NOT_FOUND);

		Long unReadCount = messageRepository.countAllByReceiverIdAndIsRead(userId, false);
		Long readCount = messageRepository.countAllByReceiverIdAndIsRead(userId, true);

		return GetMessageCountDto.Response.builder()
			.unreadMessageCount(unReadCount)
			.readMessageCount(readCount)
			.build();
	}

	@Transactional(readOnly = true)
	public void readMessage(String messageId, Long userId) {
		validateUserExists(userId, MessageErrorCode.RECEIVER_NOT_FOUND);

		Message message = messageRepository.findById(messageId)
			.orElseThrow(() -> new BrainPixException(MessageErrorCode.MESSAGE_NOT_FOUND));

		message.readMessage();

		messageRepository.save(message);
	}

	private User getUser(Long userId, ErrorCode errorCode) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(errorCode));
	}

	private Page<Message> getMessageListBySearchType(Long userId, MessageSearchType searchType,
		PageRequest pageRequest) {
		if (searchType == MessageSearchType.ALL) {
			return messageRepository.findByReceiverIdOrSenderId(userId, userId, pageRequest);
		} else if (searchType == MessageSearchType.SEND) {
			return messageRepository.findAllBySenderId(userId, pageRequest);
		} else if (searchType == MessageSearchType.RECEIVED) {
			return messageRepository.findAllByReceiverId(userId, pageRequest);
		} else {
			return Page.empty();
		}
	}

	public void validateUserExists(Long userId, ErrorCode errorCode) {
		if (!userRepository.existsById(userId)) {
			throw new BrainPixException(errorCode);
		}
	}
}
