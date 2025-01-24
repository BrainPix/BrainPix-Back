package com.brainpix.alarm.dto;

import java.util.Map;

import com.brainpix.alarm.template.AlarmType;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class AlarmEventDto {
	private final AlarmType alarmType;
	private final Long receiverId;
	private final Map<String, String> parameters;
}
