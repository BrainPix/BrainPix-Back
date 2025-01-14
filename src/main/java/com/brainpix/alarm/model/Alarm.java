package com.brainpix.alarm.model;

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
@Document(collection = "alarm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseTimeEntity {

	@Id
	private String id;

	private Long receiverId;

	private Boolean isRead;

	private Boolean trashed;

	private AlarmInfo alarmInfo;

	public void readAlarm() {
		this.isRead = true;
	}
}
