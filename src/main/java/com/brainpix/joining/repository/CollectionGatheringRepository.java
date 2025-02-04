package com.brainpix.joining.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.user.entity.User;

public interface CollectionGatheringRepository extends JpaRepository<CollectionGathering, Long> {

	// 협업 횟수 조회 (승낙된 협업)
	Long countByJoinerIdAndAccepted(Long joinerId, Boolean accepted);

	Long countByJoinerIdAndInitialGathering(Long joinerId, Boolean initialGathering);

	Page<CollectionGathering> findByJoinerAndAcceptedIsFalse(User joiner, Pageable pageable);

	Page<CollectionGathering> findByJoinerAndAcceptedIsTrue(User joiner, Pageable pageable);

	// 개최 인원 등록 정보 조회
	@Query("SELECT cg FROM CollectionGathering cg " +
		"JOIN FETCH cg.collaborationRecruitment cr " +
		"WHERE cr.parentCollaborationHub.id = :collaborationHubId " +
		"AND cg.initialGathering = true")
	List<CollectionGathering> findByCollaborationHubId(@Param("collaborationHubId") Long collaborationHubId);


	// 이미 지원했던 분야인지 확인
	boolean existsByJoinerIdAndCollaborationRecruitmentId(Long joinerId, Long collaborationRecruitmentId);

}

