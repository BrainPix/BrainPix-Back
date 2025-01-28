package com.brainpix.joining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;

public interface CollectionGatheringRepository extends JpaRepository<CollectionGathering, Long> {

	// 협업 횟수 조회 (승낙된 협업)
	Long countByJoinerIdAndAccepted(Long joinerId, Boolean accepted);

	@Query("SELECT COUNT(g) FROM CollectionGathering g WHERE g.collaborationRecruitment = :recruitment AND g.accepted = true")
	long countAcceptedByRecruitment(CollaborationRecruitment recruitment);

	@Query("SELECT g FROM CollectionGathering g WHERE g.collaborationRecruitment = :recruitment AND g.accepted IS NULL")
	List<CollectionGathering> findWaitingByRecruitment(CollaborationRecruitment recruitment);

	@Query("SELECT g FROM CollectionGathering g WHERE g.collaborationRecruitment = :recruitment AND g.accepted = true")
	List<CollectionGathering> findAcceptedByRecruitment(CollaborationRecruitment recruitment);

	@Query("SELECT g FROM CollectionGathering g WHERE g.collaborationRecruitment = :recruitment AND g.isStartingMember = true")
	List<CollectionGathering> findStartingMembersByRecruitment(CollaborationRecruitment recruitment);
}

