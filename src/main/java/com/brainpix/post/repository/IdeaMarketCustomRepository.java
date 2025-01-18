package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public interface IdeaMarketCustomRepository {

	// 검색 기능을 포함한 아이디어 조회
	Page<Object[]> findIdeaListWithSaveCount(IdeaMarketType ideaMarketType, String keyword, Specialization category,
		Boolean onlyCompany, SortType sortType, Pageable pageable);
}
