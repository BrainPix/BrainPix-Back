package com.brainpix.post.repository;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaMarketPurchasingRepository extends JpaRepository<IdeaMarketPurchasing, Long> {
    // 특정 게시물의 구매자 정보 조회
    List<IdeaMarketPurchasing> findByIdeaMarketId(Long ideaMarketId);

    // 특정 구매자가 구매한 게시물 조회
    List<IdeaMarketPurchasing> findByBuyerId(Long buyerId);
}