package com.brainpix.post.repository.collaborationhub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;

@Repository
public interface CollaborationRecruitmentRepository extends
	JpaRepository<CollaborationRecruitment, Long> {
	@Query("""
		SELECT DISTINCT r
		FROM CollaborationRecruitment r
		    LEFT JOIN FETCH r.gathering g
		    LEFT JOIN FETCH r.purchases p
		    LEFT JOIN FETCH p.joiner u
		WHERE r.parentCollaborationHub.id = :hubId
		""")
	List<CollaborationRecruitment> findAllWithPurchasesByHubId(@Param("hubId") Long hubId);
}