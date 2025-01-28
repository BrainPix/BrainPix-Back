package com.brainpix.joining.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;

public interface RequestTaskPurchasingRepository extends JpaRepository<RequestTaskPurchasing, Long> {

	// 이미 지원했는지 여부 확인
	boolean existsByBuyerIdAndRequestTaskRecruitmentId(Long buyerId, Long requestTaskRecruitmentId);
}
