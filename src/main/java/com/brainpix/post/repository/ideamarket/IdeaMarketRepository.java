package com.brainpix.post.repository.ideamarket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.idea_market.IdeaMarket;

@Repository
public interface IdeaMarketRepository extends JpaRepository<IdeaMarket, Long> {
	@Query("""
		SELECT DISTINCT i FROM IdeaMarket i
		LEFT JOIN FETCH i.price
		WHERE i.writer.id = :writerId
		""")
	List<IdeaMarket> findAllByWriterId(@Param("writerId") Long writerId);
}
