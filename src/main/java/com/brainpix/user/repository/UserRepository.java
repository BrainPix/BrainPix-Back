package com.brainpix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

// 테스트 하려고 임의로 만듬