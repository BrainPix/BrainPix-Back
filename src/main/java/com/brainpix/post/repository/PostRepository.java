package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.Post;
import com.brainpix.user.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findByWriter(User writer, Pageable pageable);
}