package com.brainpix.message.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.brainpix.message.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	Page<Message> findAllByReceiverId(Long receiverId, Pageable pageable);
	Page<Message> findAllByReceiverIdAndIsRead(Long receiverId, Boolean isRead, Pageable pageable);
	Long countAllByReceiverIdAndIsRead(Long receiverId, Boolean isRead);
}
