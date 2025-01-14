package com.brainpix.profile.repository;

import com.brainpix.profile.entity.Career;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findByUserId(Long userId);
    @Transactional
    void deleteByUserId(Long userId);
}