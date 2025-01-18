package com.brainpix.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
