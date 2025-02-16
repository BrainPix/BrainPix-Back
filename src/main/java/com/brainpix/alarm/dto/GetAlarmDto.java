package com.brainpix.alarm.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * 읽지 않은 알람을 가져오는 API 의 응답 DTO
 */
public class GetAlarmDto {

	/**
	 * 읽지 않은 알람을 가져오는 API 의 응답
	 */
	@Getter
	@Builder
	public static class Response {
		private Integer totalPage;
		private Integer currentPage;
		private Long totalElements;
		private Integer listSize;
		private Boolean isLast;
		private List<AlarmDetail> alarmDetailList;
	}

	/**
	 * 알람 상세 정보
	 */
	@Getter
	@Builder
	public static class AlarmDetail {
		private String alarmId;
		private Boolean isRead;
		private String header;
		private String message;
		private String redirectUrl;
	}
}
