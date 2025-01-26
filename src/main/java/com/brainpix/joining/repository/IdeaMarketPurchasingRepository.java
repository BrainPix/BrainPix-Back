package com.brainpix.joining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.user.entity.User;

@Repository
public interface IdeaMarketPurchasingRepository
	extends JpaRepository<IdeaMarketPurchasing, Long> {
	List<IdeaMarketPurchasing> findByBuyer(User buyer);
}