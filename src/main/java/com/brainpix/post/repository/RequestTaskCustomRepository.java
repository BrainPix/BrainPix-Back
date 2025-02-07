package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.entity.request_task.RequestTaskType;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public interface RequestTaskCustomRepository {

	// 검색 기능을 포함한 요청 과제 조회
	Page<Object[]> findRequestTaskListWithSaveCount(Long userId, RequestTaskType requestTaskType, String keyword,
		Specialization category, Boolean onlyCompany, SortType sortType, Pageable pageable);

	// (OPEN_IDEA, TECH_ZONE)으로 구분한 뒤, 모든 요청 과제 중에서 저장순으로 조회
	Page<Object[]> findPopularRequestTaskListWithSaveCount(Long userId, RequestTaskType requestTaskType,
		Pageable pageable);
}
