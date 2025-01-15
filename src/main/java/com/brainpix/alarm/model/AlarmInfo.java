package com.brainpix.alarm.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmInfo {
	private String name;
	private String header;
	private String message;
	private String redirectUrl;
}
