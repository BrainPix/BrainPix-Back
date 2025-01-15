package com.brainpix.alarm.dto;

import lombok.Builder;
import lombok.Getter;

public class GetUnreadAlarmDto {

	@Getter
	@Builder
	public static class Response {
		private Long alarmCount;
	}
}
