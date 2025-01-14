package com.brainpix.profile.repository;

import com.brainpix.profile.entity.IndividualProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualProfileRepository extends JpaRepository<IndividualProfile, Long> {
    Optional<IndividualProfile> findByUserId(Long userId);
}