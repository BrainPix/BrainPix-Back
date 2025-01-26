package com.brainpix.joining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.post.entity.idea_market.IdeaMarket;

@Repository
public interface IdeaMarketPurchasingRepository extends JpaRepository<IdeaMarketPurchasing, Long> {

	// 특정 아이디어마켓 글에 대한 구매 정보 전체 조회
	List<IdeaMarketPurchasing> findByIdeaMarket(IdeaMarket ideaMarket);
}