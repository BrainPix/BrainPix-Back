package com.brainpix.kakaopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;

public interface NamedLockRepository extends JpaRepository<IdeaMarketPurchasing, Long> {

	// 네임드 락 획득
	@Query(value = "SELECT GET_LOCK(:lockName, 10)", nativeQuery = true)
	Integer getLock(@Param("lockName") String lockName);

	// 네임드 락 해제
	@Query(value = "SELECT RELEASE_LOCK(:lockName)", nativeQuery = true)
	Integer releaseLock(@Param("lockName") String lockName);
}
