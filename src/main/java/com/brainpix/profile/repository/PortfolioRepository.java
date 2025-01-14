package com.brainpix.profile.repository;

import com.brainpix.profile.entity.Portfolio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    /**
     * 사용자 ID로 포트폴리오 조회
     * @param profileId 프로필 ID
     * @return 포트폴리오 리스트
     */
    List<Portfolio> findByProfileId(Long profileId);

    /**
     * 특정 프로필 ID와 연관된 포트폴리오를 모두 삭제
     * @param profileId 프로필 ID
     */
    void deleteByProfileId(Long profileId);
}