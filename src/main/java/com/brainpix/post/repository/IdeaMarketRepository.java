package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.idea_market.IdeaMarket;

public interface IdeaMarketRepository extends JpaRepository<IdeaMarket, Long>, IdeaMarketCustomRepository {
}
