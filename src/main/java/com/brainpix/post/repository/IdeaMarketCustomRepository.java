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

	// (IDEA_SOLUTION, MARKET_PLACE)로 구분한 뒤, 모든 아이디어 중에서 저장순으로 조회
	Page<Object[]> findPopularIdeaListWithSaveCount(IdeaMarketType ideaMarketType, Pageable pageable);
}
