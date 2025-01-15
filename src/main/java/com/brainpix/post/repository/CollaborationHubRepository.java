package com.brainpix.post.repository;

import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaborationHubRepository extends JpaRepository<CollaborationHub, Long> {
    @Query("""
        SELECT DISTINCT h FROM CollaborationHub h
        LEFT JOIN FETCH h.recruitments r
        LEFT JOIN FETCH r.gathering g
        WHERE h.writer.id = :writerId
        """)
    List<CollaborationHub> findAllByWriterIdFetchJoin(@Param("writerId") Long writerId);
}