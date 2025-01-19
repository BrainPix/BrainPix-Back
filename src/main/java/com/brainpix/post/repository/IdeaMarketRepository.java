package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.idea_market.IdeaMarket;

@Repository
public interface IdeaMarketRepository extends JpaRepository<IdeaMarket, Long> {
}
