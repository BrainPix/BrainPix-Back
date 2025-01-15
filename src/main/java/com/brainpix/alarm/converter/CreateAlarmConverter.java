package com.brainpix.alarm.converter;

import java.util.Map;

import com.brainpix.alarm.dto.CreateAlarmDto;
import com.brainpix.alarm.model.Alarm;
import com.brainpix.alarm.model.AlarmInfo;
import com.brainpix.alarm.template.AlarmTemplate;
import com.brainpix.alarm.utils.AlarmInfoParser;

public class CreateAlarmConverter {

	public static Alarm convertToAlarm(CreateAlarmDto.Request request) {
		Map<String, String> parameters = Map.of("postType", request.getPostType(), "postWriter",
			request.getPostWriter(),
			"commentWriter", request.getCommentWriter(), "applicant", request.getApplicant());
		return Alarm.builder()
			.receiverId(request.getReceiverId())
			.isRead(false)
			.trashed(false)
			.alarmInfo(convertToAlarmInfo(AlarmTemplate.valueOf(request.getAlarmType().toUpperCase()), parameters))
			.build();
	}

	public static AlarmInfo convertToAlarmInfo(AlarmTemplate template, Map<String, String> parameters) {
		return AlarmInfoParser.get(template, parameters);
	}
}
