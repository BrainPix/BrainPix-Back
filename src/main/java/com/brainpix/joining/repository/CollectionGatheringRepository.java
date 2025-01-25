package com.brainpix.joining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.user.entity.User;

public interface CollectionGatheringRepository extends JpaRepository<CollectionGathering, Long> {

	// 협업 횟수 조회 (승낙된 협업)
	Long countByJoinerIdAndAccepted(Long joinerId, Boolean accepted);

	List<CollectionGathering> findByJoinerAndAcceptedIsFalse(User joiner);

	List<CollectionGathering> findByJoinerAndAcceptedIsTrue(User joiner);
}
