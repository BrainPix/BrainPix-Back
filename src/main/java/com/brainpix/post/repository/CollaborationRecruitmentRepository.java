package com.brainpix.post.repository;

import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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