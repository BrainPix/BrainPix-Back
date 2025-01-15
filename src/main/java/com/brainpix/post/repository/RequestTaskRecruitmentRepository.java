package com.brainpix.post.repository;

import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestTaskRecruitmentRepository extends JpaRepository<RequestTaskRecruitment, Long> {
    // 특정 Task에 해당하는 모집 분야 조회
    List<RequestTaskRecruitment> findByRequestTaskId(Long requestTaskId);
}
