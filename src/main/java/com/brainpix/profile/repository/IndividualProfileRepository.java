package com.brainpix.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.user.entity.User;

public interface IndividualProfileRepository extends JpaRepository<IndividualProfile, Long> {
	Optional<IndividualProfile> findByUser(User user);
}
