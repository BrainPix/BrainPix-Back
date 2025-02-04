package com.brainpix.joining.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

	public static Pageable withSort(Pageable pageable, String sortBy, Sort.Direction direction) {
		return PageRequest.of(
			pageable.getPageNumber(),
			pageable.getPageSize(),
			Sort.by(direction, sortBy)
		);
	}
}
