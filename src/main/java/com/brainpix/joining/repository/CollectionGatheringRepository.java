package com.brainpix.joining.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.joining.entity.purchasing.CollectionGathering;

public interface CollectionGatheringRepository extends JpaRepository<CollectionGathering, Long> {

	// 협업 횟수 조회 (승낙된 협업)
	Long countByJoinerIdAndAccepted(Long joinerId, Boolean accepted);

	// 이미 지원했던 분야인지 확인
	boolean existsByJoinerIdAndCollaborationRecruitmentId(Long joinerId, Long collaborationRecruitmentId);
}
