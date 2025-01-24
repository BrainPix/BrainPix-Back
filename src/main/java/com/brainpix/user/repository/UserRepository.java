package com.brainpix.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByIdentifier(String identifier);

	Optional<User> findByNickName(String nickName);
}
