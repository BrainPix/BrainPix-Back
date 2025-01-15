package com.brainpix.post.repository;

import com.brainpix.post.entity.idea_market.IdeaMarket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaMarketRepository extends JpaRepository<IdeaMarket, Long> {
    List<IdeaMarket> findByWriterId(Long userId);
}