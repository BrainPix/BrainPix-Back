package com.brainpix.joining.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.request_task.RequestTaskRecruitment;

public interface RequestTaskRecruitmentRepository extends JpaRepository<RequestTaskRecruitment, Long> {

	/**
	 * 사용자가 승인된 요청 과제 횟수
	 */
	Long countByJoinerIdAndAccepted(Long joinerId, Boolean accepted);
}

