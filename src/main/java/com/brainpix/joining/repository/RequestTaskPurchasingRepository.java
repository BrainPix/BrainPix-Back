package com.brainpix.joining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.user.entity.User;

@Repository
public interface RequestTaskPurchasingRepository
	extends JpaRepository<RequestTaskPurchasing, Long> {

	// "수락" 상태(accepted = true) 리스트
	List<RequestTaskPurchasing> findByBuyerAndAcceptedIsTrue(User buyer);

	// "거절" 상태(accepted = false) 리스트
	List<RequestTaskPurchasing> findByBuyerAndAcceptedIsFalse(User buyer);
}