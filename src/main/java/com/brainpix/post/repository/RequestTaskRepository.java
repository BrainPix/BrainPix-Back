package com.brainpix.post.repository;


import com.brainpix.post.entity.request_task.RequestTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTaskRepository extends JpaRepository<RequestTask, Long> {
    List<RequestTask> findByWriterId(Long writerId);
}
