package com.brainpix.profile.repository;

import com.brainpix.profile.entity.Stack;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
    List<Stack> findByUserId(Long userId);
    @Transactional
    void deleteByIndividualProfileId(Long individualProfileId);
}