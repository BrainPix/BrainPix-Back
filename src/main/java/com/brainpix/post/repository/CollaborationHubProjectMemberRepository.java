package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.collaboration_hub.CollaborationHubProjectMember;

@Repository
public interface CollaborationHubProjectMemberRepository extends JpaRepository<CollaborationHubProjectMember, Long> {
}
