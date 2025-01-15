package com.brainpix.alarm.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.brainpix.alarm.converter.CreateAlarmConverter;
import com.brainpix.alarm.converter.GetAlarmConverter;
import com.brainpix.alarm.dto.CreateAlarmDto;
import com.brainpix.alarm.dto.GetAlarmDto;
import com.brainpix.alarm.dto.GetUnreadAlarmDto;
import com.brainpix.alarm.model.Alarm;
import com.brainpix.alarm.repository.AlarmRepository;
import com.brainpix.api.code.error.AlarmErrorCode;
import com.brainpix.api.exception.BrainPixException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;

	/**
	 * 알림을 읽음 처리합니다
	 * @param alarmId 알림 ID
	 * @throws BrainPixException 알림이 이미 읽음 처리된 경우
	 */
	public void readAlarm(String alarmId, Long userId) {
		Alarm alarm = getAlarm(alarmId, userId);

		if (alarm.getIsRead()) {
			throw new BrainPixException(AlarmErrorCode.ALARM_ALREADY_READ);
		}
		alarm.readAlarm();
		alarmRepository.save(alarm);
	}

	/**
	 * 알림 목록을 조회합니다
	 * <p>조회한 알람을 우선순위로 정렬하여 반환합니다</p>
	 * @param pageable 페이징 정보(페이지 번호, 페이지 크기)
	 * @return GetAlarmDto.Response
	 */
	public GetAlarmDto.Response getAlarm(Pageable pageable, Long receiverId) {
		// sort 추가하여 isRead = false 먼저 보여줌
		PageRequest pageRequest = getPageRequestOrderByCreatedAt(pageable);

		Page<Alarm> alarmPage = alarmRepository.findByReceiverIdAndTrashed(receiverId, false, pageRequest);

		return GetAlarmConverter.convertToResponse(alarmPage);
	}

	/**
	 * 휴지통 알림 목록을 조회합니다
	 * <p>휴지통에 있는 알람을 생성일자로 정렬하여 반환합니다</p>
	 * @param pageable 페이징 정보(페이지 번호, 페이지 크기)
	 * @return GetAlarmDto.Response
	 */
	public GetAlarmDto.Response getTrashAlarm(Pageable pageable, Long receiverId) {
		PageRequest pageRequest = getPageRequestOrderByCreatedAt(pageable);

		Page<Alarm> alarmPage = alarmRepository.findByReceiverIdAndTrashed(receiverId, true, pageRequest);

		return GetAlarmConverter.convertToResponse(alarmPage);
	}

	/**
	 * 알림을 생성합니다
	 * @param request 알림 생성 요청 정보
	 * @return CreateAlarmDto.Response
	 */
	public CreateAlarmDto.Response createAlarm(CreateAlarmDto.Request request) {

		Alarm alarm = CreateAlarmConverter.convertToAlarm(request);

		alarmRepository.save(alarm);

		return CreateAlarmDto.Response.builder().alarmId(alarm.getId()).build();
	}

	/**
	 * 읽지 않은 알림 개수를 조회합니다
	 * @param userId 알람 수신 사용자 ID
	 * @return GetUnreadAlarmDto.Response
	 */
	public GetUnreadAlarmDto.Response getUnreadAlarmCount(Long userId) {

		Long alarmCount = alarmRepository.countAlarmByReceiverIdAndIsRead(userId, false);

		return GetUnreadAlarmDto.Response.builder().alarmCount(alarmCount).build();
	}

	/**
	 * 알림을 휴지통으로 보냅니다
	 * @param alarmId 알림 ID
	 * @param userId 사용자 ID
	 */
	public void addTrashAlarm(String alarmId, Long userId) {
		Alarm alarm = getAlarm(alarmId, userId);

		if (alarm.getTrashed()) {
			throw new BrainPixException(AlarmErrorCode.ALARM_ALREADY_TRASHED);
		}

		alarm.addTrash();

		alarmRepository.save(alarm);
	}

	/**
	 * 휴지통에서 알림을 복원합니다
	 * @param alarmId 알림 ID
	 * @param userId 사용자 ID
	 */
	public void restoreAlarm(String alarmId, Long userId) {
		Alarm alarm = getAlarm(alarmId, userId);

		if (!alarm.getTrashed()) {
			throw new BrainPixException(AlarmErrorCode.ALARM_NOT_TRASHED);
		}

		alarm.restore();

		alarmRepository.save(alarm);
	}

	/**
	 * 하나의 알림을 삭제합니다
	 * @param alarmId 알림 ID
	 * @param userId 사용자 ID
	 */
	public void deleteOneAlarm(String alarmId, Long userId) {
		Alarm alarm = getAlarm(alarmId, userId);

		if (!alarm.getTrashed()) {
			throw new BrainPixException(AlarmErrorCode.ALARM_NOT_TRASHED);
		}

		alarmRepository.delete(alarm);
	}

	/**
	 * 휴지통에 있는 모든 알림을 삭제합니다
	 * @param userId 사용자 ID
	 */
	public void deleteAllAlarm(Long userId) {
		List<Alarm> trashedAlarm = alarmRepository.findAllByReceiverIdAndTrashed(userId, true);

		alarmRepository.deleteAll(trashedAlarm);
	}

	/**
	 * 알림 ID로 알림을 조회합니다
	 * @param alarmId 알림 ID
	 * @param userId 사용자 ID
	 * @throws BrainPixException 알림이 존재하지 않거나 알림 수신자가 아닌 경우
	 * @return Alarm
	 */
	private Alarm getAlarm(String alarmId, Long userId) {
		Alarm alarm = alarmRepository.findById(alarmId)
			.orElseThrow(() -> new BrainPixException(AlarmErrorCode.ALARM_NOT_FOUND));

		if (!Objects.equals(alarm.getReceiverId(), userId)) {
			throw new BrainPixException(AlarmErrorCode.NOT_ALARM_RECEIVER);
		}

		return alarm;
	}

	/**
	 * 생성일자를 기준으로 내림차순 정렬이 추가된 PageRequest를 반환합니다
	 * @param pageable 페이징 정보
	 * @return PageRequest
	 */
	private PageRequest getPageRequestOrderByCreatedAt(Pageable pageable) {
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
			Sort.by(Sort.Order.desc("createdAt")));
	}
}
