package com.brainpix.alarm.dto;

import lombok.Builder;
import lombok.Getter;

public class CreateAlarmDto {

	@Getter
	@Builder
	public static class Request {
		private Long receiverId;
		private String alarmType;
		private String postType;
		private String postWriter;
		private String commentWriter;
		private String applicant;
	}

	@Getter
	@Builder
	public static class Response {
		private String alarmId;
	}
}
