package com.brainpix.joining.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.user.entity.User;

@Repository
public interface RequestTaskPurchasingRepository
	extends JpaRepository<RequestTaskPurchasing, Long> {

	// "수락" 상태(accepted = true) 리스트
	Page<RequestTaskPurchasing> findByBuyerAndAcceptedIsTrue(User buyer, Pageable pageable);

	// "거절" 상태(accepted = false) 리스트
	Page<RequestTaskPurchasing> findByBuyerAndAcceptedIsFalse(User buyer, Pageable pageable);

	// 이미 지원했는지 여부 확인
	boolean existsByBuyerIdAndRequestTaskRecruitmentId(Long buyerId, Long requestTaskRecruitmentId);
  
  Long countByBuyerIdAndAccepted(Long joinerId, Boolean accepted);

}
