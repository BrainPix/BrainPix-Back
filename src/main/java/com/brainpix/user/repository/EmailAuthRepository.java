package com.brainpix.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.user.entity.EmailAuth;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
	Optional<EmailAuth> findFirstByEmailOrderByCreatedAtDesc(String email);
}
