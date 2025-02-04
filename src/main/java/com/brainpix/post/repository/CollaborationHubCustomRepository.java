package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public interface CollaborationHubCustomRepository {

	// 검색 기능을 포함한 협업 광장 조회
	Page<Object[]> findCollaborationListWithSaveCount(String keyword,
		Specialization category,
		Boolean onlyCompany, SortType sortType, Pageable pageable);
}
