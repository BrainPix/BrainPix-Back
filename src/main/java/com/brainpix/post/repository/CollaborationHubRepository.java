package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.user.entity.User;

@Repository
public interface CollaborationHubRepository
	extends JpaRepository<CollaborationHub, Long>, CollaborationHubCustomRepository {
	Page<CollaborationHub> findByWriter(User writer, Pageable pageable);
}
