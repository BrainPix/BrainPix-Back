package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;

@Repository
public interface CollaborationHubRecruitmentRepository extends JpaRepository<CollaborationRecruitment, Long> {
}
