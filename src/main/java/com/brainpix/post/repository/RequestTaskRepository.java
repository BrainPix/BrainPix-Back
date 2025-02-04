package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.request_task.RequestTask;

@Repository
public interface RequestTaskRepository extends JpaRepository<RequestTask, Long>, RequestTaskCustomRepository {
}
