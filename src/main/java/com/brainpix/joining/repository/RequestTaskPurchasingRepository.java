package com.brainpix.joining.repository;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestTaskPurchasingRepository extends JpaRepository<RequestTaskPurchasing, Long> {

    // 특정 모집 분야에 지원한 목록 조회
    List<RequestTaskPurchasing> findByRequestTaskRecruitmentId(Long recruitmentId);

    // 특정 Task에 지원한 전체 목록 조회
    List<RequestTaskPurchasing> findByRequestTaskRecruitment_RequestTaskId(Long requestTaskId);
}
