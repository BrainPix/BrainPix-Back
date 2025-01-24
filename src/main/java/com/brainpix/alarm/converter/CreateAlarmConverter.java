package com.brainpix.alarm.converter;

import java.util.Map;

import com.brainpix.alarm.dto.AlarmEventDto;
import com.brainpix.alarm.model.Alarm;
import com.brainpix.alarm.model.AlarmInfo;
import com.brainpix.alarm.template.AlarmTemplate;
import com.brainpix.alarm.utils.AlarmInfoParser;

public class CreateAlarmConverter {

	public static Alarm convertToAlarm(AlarmEventDto request) {
		return Alarm.builder()
			.receiverId(request.getReceiverId())
			.isRead(false)
			.trashed(false)
			.alarmInfo(convertToAlarmInfo(AlarmTemplate.valueOf(request.getAlarmType().name()), request.getParameters()))
			.build();
	}

	public static AlarmInfo convertToAlarmInfo(AlarmTemplate template, Map<String, String> parameters) {
		return AlarmInfoParser.get(template, parameters);
	}
}
