package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;

@Repository
public interface CollaborationHubRepository extends JpaRepository<CollaborationHub, Long>, CollaborationHubCustomRepository {
}
