package com.brainpix.post.repository.requesttask;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.request_task.RequestTaskRecruitment;

@Repository
public interface RequestTaskRecruitmentRepository extends JpaRepository<RequestTaskRecruitment, Long> {

	@Query("""
		SELECT DISTINCT r 
		FROM RequestTaskRecruitment r
		LEFT JOIN FETCH r.purchases p
		LEFT JOIN FETCH p.buyer b
		WHERE r.requestTask.id = :taskId
		""")
	List<RequestTaskRecruitment> findAllWithPurchasesByTaskId(@Param("taskId") Long taskId);
}
