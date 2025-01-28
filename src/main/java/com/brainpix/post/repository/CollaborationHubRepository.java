package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;

public interface CollaborationHubRepository extends JpaRepository<CollaborationHub, Long> {
	List<CollaborationHub> findByWriterId(Long userId);
}