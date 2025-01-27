package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;

public interface RequestTaskPurchasingRepository
	extends JpaRepository<RequestTaskPurchasing, Long> {

	// 특정 모집(RequestTaskRecruitment)에 대한 지원(“purchasing”) 목록
	List<RequestTaskPurchasing> findByRequestTaskRecruitment(RequestTaskRecruitment recruitment);

}