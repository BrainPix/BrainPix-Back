package com.brainpix.post.repository.ideamarket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;

@Repository
public interface IdeaMarketPurchasingRepository extends JpaRepository<IdeaMarketPurchasing, Long> {
	@Query("""
		SELECT DISTINCT p FROM IdeaMarketPurchasing p
		JOIN FETCH p.ideaMarket
		WHERE p.ideaMarket.id = :ideaMarketId
		""")
	List<IdeaMarketPurchasing> findAllByIdeaMarketId(@Param("ideaMarketId") Long ideaMarketId);
}

