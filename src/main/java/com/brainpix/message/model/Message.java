package com.brainpix.message.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTimeEntity {

	@Id
	private String id;

	private Long senderId;

	private Long receiverId;

	private Boolean isRead;

	private String title;

	private String content;

	public void readMessage() {
		this.isRead = true;
	}
}
