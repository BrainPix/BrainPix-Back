package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.request_task.RequestTaskRecruitment;

@Repository
public interface RequestTaskRecruitmentRepository extends JpaRepository<RequestTaskRecruitment, Long> {
	List<RequestTaskRecruitment> findByRequestTaskId(Long taskId);
}
