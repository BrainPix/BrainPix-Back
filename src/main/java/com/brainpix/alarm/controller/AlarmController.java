package com.brainpix.alarm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainpix.alarm.dto.GetAlarmDto;
import com.brainpix.alarm.dto.GetUnreadAlarmDto;
import com.brainpix.alarm.service.AlarmService;
import com.brainpix.api.ApiResponse;
import com.brainpix.security.authorization.AllUser;
import com.brainpix.security.authorization.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
@Tag(name = "Alarm", description = "알림 관련 API")
public class AlarmController {

	private final AlarmService alarmService;

	@AllUser
	@PatchMapping("/read/{alarmId}")
	@Operation(summary = "알림 읽음 처리 API", description = "알림을 읽음 처리합니다.")
	public ResponseEntity<ApiResponse<Void>> readAlarm(@PathVariable String alarmId, @UserId Long userId) {

		alarmService.readAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@GetMapping
	@Operation(summary = "알림 다건 조회 API", description = "여러건의 알림을 조회합니다.")
	public ResponseEntity<ApiResponse<GetAlarmDto.Response>> getAlarmList(Pageable pageable, @UserId Long userId) {

		GetAlarmDto.Response data = alarmService.getAlarm(pageable, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@AllUser
	@GetMapping("/trash")
	@Operation(summary = "휴지통 알림 조회 API", description = "휴지통에 있는 알림들을 조회합니다.")
	public ResponseEntity<ApiResponse<GetAlarmDto.Response>> getTrashAlarmList(Pageable pageable, @UserId Long userId) {

		GetAlarmDto.Response data = alarmService.getTrashAlarm(pageable, userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@AllUser
	@GetMapping("/count")
	@Operation(summary = "알림 개수 조회 API", description = "알림 개수를 조회합니다.")
	public ResponseEntity<ApiResponse<GetUnreadAlarmDto.Response>> getUnreadAlarmCount(@UserId Long userId) {

		GetUnreadAlarmDto.Response data = alarmService.getUnreadAlarmCount(userId);

		return ResponseEntity.ok(ApiResponse.success(data));
	}

	@AllUser
	@PatchMapping("/trash/{alarmId}")
	@Operation(summary = "알림 휴지통으로 보내기 API", description = "알림 하나를 휴지통으로 보냅니다.")
	public ResponseEntity<ApiResponse<Void>> addTrashAlarm(@PathVariable String alarmId, @UserId Long userId) {

		alarmService.addTrashAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@PatchMapping("/restore/{alarmId}")
	@Operation(summary = "휴지통에서 알림 복구 API", description = "휴지통에서 알림을 복구합니다.")
	public ResponseEntity<ApiResponse<Void>> restoreAlarm(@PathVariable String alarmId, @UserId Long userId) {

		alarmService.restoreAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@DeleteMapping("/delete/{alarmId}")
	@Operation(summary = "휴지통에서 알림 하나 삭제 API", description = "휴지통에 있는 알림 하나를 삭제합니다.")
	public ResponseEntity<ApiResponse<Void>> deleteAlarm(@PathVariable String alarmId, @UserId Long userId) {

		alarmService.deleteOneAlarm(alarmId, userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@AllUser
	@DeleteMapping("/delete")
	@Operation(summary = "휴지통에서 알림 전체 삭제 API", description = "휴지통에 있는 알림 전체를 삭제합니다.")
	public ResponseEntity<ApiResponse<Void>> deleteAllAlarm(@UserId Long userId) {

		alarmService.deleteAllAlarm(userId);

		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

}
