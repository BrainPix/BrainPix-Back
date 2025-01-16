package com.brainpix.alarm.utils;

import java.util.Map;

import com.brainpix.alarm.template.AlarmTemplate;
import com.brainpix.alarm.model.AlarmInfo;

public class AlarmInfoParser {

	public static AlarmInfo get(AlarmTemplate template, Map<String, String> params) {
		String message = parseTemplate(template.getMessageTemplate(), params);
		String header = parseTemplate(template.getHeaderTemplate(), params);
		String redirectUrl = parseTemplate(template.getRedirectUrl(), params);
		return AlarmInfo.builder().name(template.name()).header(header).message(message).redirectUrl(redirectUrl).build();
	}

	private static String parseTemplate(String template, Map<String, String> params) {
		String parsedTemplate = template;
		for (String key : params.keySet()) {
			parsedTemplate = parsedTemplate.replace("${" + key + "}", params.get(key));
		}
		return parsedTemplate;
	}
}
