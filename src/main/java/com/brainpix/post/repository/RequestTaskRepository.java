package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.user.entity.User;

@Repository
public interface RequestTaskRepository extends JpaRepository<RequestTask, Long> {

	List<RequestTask> findByWriter(User writer);
}
