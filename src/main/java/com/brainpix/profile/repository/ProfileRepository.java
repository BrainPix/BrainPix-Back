package com.brainpix.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.profile.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByUserId(Long userId);

}
