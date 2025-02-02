package com.brainpix.post.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DeadlineUtils {

	public static String toDDayFormat(LocalDateTime deadline) {
		if (deadline == null) {
			return null;
		}
		// 오늘 날짜와 deadline(날짜부) 간의 차이
		long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), deadline.toLocalDate());

		if (daysBetween > 0) {
			return "D-" + daysBetween;
		} else if (daysBetween == 0) {
			return "D-Day";
		} else {
			return "D+" + Math.abs(daysBetween);
		}
	}
}