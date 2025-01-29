package com.brainpix.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.profile.entity.CompanyProfile;
import com.brainpix.user.entity.User;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {
	Optional<CompanyProfile> findByUser(User user);
}
