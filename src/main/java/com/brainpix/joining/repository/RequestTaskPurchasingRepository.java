package com.brainpix.joining.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;

public interface RequestTaskPurchasingRepository extends JpaRepository<RequestTaskPurchasing, Long> {
	Long countByBuyerIdAndAccepted(Long joinerId, Boolean accepted);
}
