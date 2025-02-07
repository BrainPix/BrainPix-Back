package com.brainpix.user.repository;

import java.util.List;
import java.util.Set;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAllByIdIn(Set<Long> userIds);

	Optional<User> findByIdentifier(String identifier);

	Optional<User> findByNickName(String nickName);

	boolean existsByIdentifier(String identifier);

	Optional<User> findByEmail(String email);
}
