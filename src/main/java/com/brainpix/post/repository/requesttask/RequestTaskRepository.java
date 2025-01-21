package com.brainpix.post.repository.requesttask;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.request_task.RequestTask;

@Repository
public interface RequestTaskRepository extends JpaRepository<RequestTask, Long> {

	@Query("""
		SELECT DISTINCT t 
		FROM RequestTask t
		LEFT JOIN FETCH t.recruitments r
		LEFT JOIN FETCH r.purchases p
		LEFT JOIN FETCH p.buyer b
		WHERE t.writer.id = :writerId
		""")
	List<RequestTask> findAllByWriterIdFetchJoin(@Param("writerId") Long writerId);
}