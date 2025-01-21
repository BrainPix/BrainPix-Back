package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.idea_market.IdeaMarket;

public interface IdeaMarketRepository extends JpaRepository<IdeaMarket, Long>, IdeaMarketCustomRepository {

	// 사용자의 아이디어 등록 횟수 카운트
	Long countByWriterId(Long writerId);
}
