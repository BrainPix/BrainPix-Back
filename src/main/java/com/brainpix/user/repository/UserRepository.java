package com.brainpix.user.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAllByIdIn(Set<Long> userIds);
}

// 테스트 하려고 임의로 만듬
