package com.brainpix.alarm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.alarm.dto.CreateAlarmDto;
import com.brainpix.alarm.dto.GetAlarmDto;
import com.brainpix.alarm.dto.GetUnreadAlarmDto;
import com.brainpix.alarm.service.AlarmService;
import com.brainpix.api.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

	private final AlarmService alarmService;

	// 알림 생성 API
	@PostMapping
	public ResponseEntity<?> createAlarm(@RequestBody CreateAlarmDto.Request request) {

		CreateAlarmDto.Response data = alarmService.createAlarm(request);

		return new ResponseEntity<>(ApiResponse.success(data), HttpStatus.CREATED);
	}

	// 알림 읽음 처리 API
	@PatchMapping("/read/{alarmId}")
	public ResponseEntity<?> readAlarm(@PathVariable String alarmId) {

		alarmService.readAlarm(alarmId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 일반 알림 조회 API
	// 시큐리티 적용 전까지 임시로 userId를 RequestParam 으로 받아서 사용
	@GetMapping
	public ResponseEntity<?> getAlarmList(Pageable pageable, @RequestParam Long userId) {

		GetAlarmDto.Response data = alarmService.getAlarm(pageable, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	// 휴지통 알림 조회 API
	// 시큐리티 적용 전까지 임시로 userId를 RequestParam 으로 받아서 사용
	@GetMapping("/trash")
	public ResponseEntity<?> getTrashAlarmList(Pageable pageable, @RequestPart Long userId) {

		GetAlarmDto.Response data = alarmService.getTrashAlarm(pageable, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@GetMapping("/count")
	public ResponseEntity<?> getUnreadAlarmCount(@RequestParam Long userId) {

		GetUnreadAlarmDto.Response data = alarmService.getUnreadAlarmCount(userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}
}
