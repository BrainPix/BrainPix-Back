package com.brainpix.alarm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

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

	// 알림 읽음 처리 API
	@PatchMapping("/read/{alarmId}")
	public ResponseEntity<ApiResponse<Void>> readAlarm(@PathVariable String alarmId, @RequestParam Long userId) {

		alarmService.readAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 일반 알림 조회 API
	// 시큐리티 적용 전까지 임시로 userId를 RequestParam 으로 받아서 사용
	@GetMapping
	public ResponseEntity<ApiResponse<GetAlarmDto.Response>> getAlarmList(Pageable pageable, @RequestParam Long userId) {

		GetAlarmDto.Response data = alarmService.getAlarm(pageable, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	// 휴지통 알림 조회 API
	// 시큐리티 적용 전까지 임시로 userId를 RequestParam 으로 받아서 사용
	@GetMapping("/trash")
	public ResponseEntity<ApiResponse<GetAlarmDto.Response>> getTrashAlarmList(Pageable pageable, @RequestPart Long userId) {

		GetAlarmDto.Response data = alarmService.getTrashAlarm(pageable, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	// 알림 수 조회 API
	@GetMapping("/count")
	public ResponseEntity<ApiResponse<GetUnreadAlarmDto.Response>> getUnreadAlarmCount(@RequestParam Long userId) {

		GetUnreadAlarmDto.Response data = alarmService.getUnreadAlarmCount(userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	// 알림 휴지통으로 보내기 API
	@PatchMapping("/trash/{alarmId}")
	public ResponseEntity<ApiResponse<Void>> addTrashAlarm(@PathVariable String alarmId, @RequestParam Long userId) {

		alarmService.addTrashAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 알림 휴지통에서 복구 API
	@PatchMapping("/restore/{alarmId}")
	public ResponseEntity<ApiResponse<Void>> restoreAlarm(@PathVariable String alarmId, @RequestParam Long userId) {

		alarmService.restoreAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 알림 삭제 API
	@DeleteMapping("/delete/{alarmId}")
	public ResponseEntity<ApiResponse<Void>> deleteAlarm(@PathVariable String alarmId, @RequestParam Long userId) {

		alarmService.deleteOneAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 알림 전체 삭제 API
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<Void>> deleteAllAlarm(@RequestParam Long userId) {

		alarmService.deleteAllAlarm(userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

}
