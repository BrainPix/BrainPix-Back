package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.user.entity.User;

@Repository
public interface RequestTaskRepository extends JpaRepository<RequestTask, Long> {

	Page<RequestTask> findByWriter(User writer, Pageable pageable);
}
