package com.brainpix.alarm.converter;

import java.util.List;

import org.springframework.data.domain.Page;

import com.brainpix.alarm.dto.GetAlarmDto;
import com.brainpix.alarm.model.Alarm;

public class GetAlarmConverter {

	public static GetAlarmDto.Response convertToResponse(Page<Alarm> alarmPage) {
		List<GetAlarmDto.AlarmDetail> alarmList = alarmPage.stream().map(GetAlarmConverter::convertToAlarmDetail).toList();
		return GetAlarmDto.Response
			.builder()
			.alarmDetailList(alarmList)
			.listSize(alarmList.size())
			.currentPage(alarmPage.getNumber())
			.isLast(alarmPage.isLast())
			.totalPage(alarmPage.getTotalPages())
			.totalElements(alarmPage.getTotalElements())
			.build();
	}

	public static GetAlarmDto.AlarmDetail convertToAlarmDetail(Alarm alarm) {
		return GetAlarmDto.AlarmDetail
			.builder()
			.alarmId(alarm.getId())
			.header(alarm.getAlarmInfo().getHeader())
			.message(alarm.getAlarmInfo().getMessage())
			.redirectUrl(alarm.getAlarmInfo().getRedirectUrl())
			.isRead(alarm.getIsRead())
			.build();
	}
}
