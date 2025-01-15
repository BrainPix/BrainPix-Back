package com.brainpix.joining.repository;


import com.brainpix.joining.entity.purchasing.CollectionGathering;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionGatheringRepository extends JpaRepository<CollectionGathering, Long> {

    // 특정 모집 분야의 지원자 목록 조회
    List<CollectionGathering> findByCollaborationRecruitmentId(Long recruitmentId);

    // 특정 모집 분야 및 지원 상태로 지원자 조회
    List<CollectionGathering> findByCollaborationRecruitmentIdAndAccepted(Long recruitmentId, Boolean accepted);
}