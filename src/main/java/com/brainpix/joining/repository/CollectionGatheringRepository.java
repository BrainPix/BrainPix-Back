package com.brainpix.joining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brainpix.joining.entity.purchasing.CollectionGathering;

public interface CollectionGatheringRepository extends JpaRepository<CollectionGathering, Long> {

	// 협업 횟수 조회 (승낙된 협업)
	Long countByJoinerIdAndAccepted(Long joinerId, Boolean accepted);

	// 개최 인원 등록 정보 조회
	@Query("SELECT cg FROM CollectionGathering cg " +
		"JOIN FETCH cg.collaborationRecruitment cr " +
		"WHERE cr.parentCollaborationHub.id = :collaborationHubId")
	List<CollectionGathering> findByCollaborationHubId(@Param("collaborationHubId") Long collaborationHubId);
}
