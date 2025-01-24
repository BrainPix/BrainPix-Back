package com.brainpix.profile.repository;

import com.brainpix.profile.entity.Portfolio;
import com.brainpix.profile.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Page<Portfolio> findByProfile(Profile profile, Pageable pageable);
}

