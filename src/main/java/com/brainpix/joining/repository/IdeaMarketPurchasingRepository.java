package com.brainpix.joining.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.user.entity.User;

@Repository
public interface IdeaMarketPurchasingRepository
	extends JpaRepository<IdeaMarketPurchasing, Long> {
	Page<IdeaMarketPurchasing> findByBuyer(User buyer, Pageable pageable);

}