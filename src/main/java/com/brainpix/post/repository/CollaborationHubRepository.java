package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;

public interface CollaborationHubRepository extends JpaRepository<CollaborationHub, Long> {
	Page<CollaborationHub> findByWriterId(Long userId, Pageable pageable);
}