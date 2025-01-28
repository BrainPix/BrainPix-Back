package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;

public interface CollaborationRecruitmentRepository extends JpaRepository<CollaborationRecruitment, Long> {
	List<CollaborationRecruitment> findByParentCollaborationHub(CollaborationHub hub);
}
