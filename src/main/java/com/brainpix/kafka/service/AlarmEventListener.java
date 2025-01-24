package com.brainpix.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.brainpix.alarm.converter.CreateAlarmConverter;
import com.brainpix.alarm.dto.AlarmEventDto;
import com.brainpix.alarm.model.Alarm;
import com.brainpix.alarm.repository.AlarmRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmEventListener {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final AlarmRepository alarmRepository;

	@KafkaListener(topics = "alarm-create", groupId = "test-group")
	public void createAlarm(AlarmEventDto request) {
		log.info("Received alarm create event: {}", request);

		Alarm alarm = CreateAlarmConverter.convertToAlarm(request);

		alarmRepository.save(alarm);
	}
}
