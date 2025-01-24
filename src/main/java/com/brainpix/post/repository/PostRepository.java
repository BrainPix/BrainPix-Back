package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.Post;
import com.brainpix.user.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByWriter(User writer);
}