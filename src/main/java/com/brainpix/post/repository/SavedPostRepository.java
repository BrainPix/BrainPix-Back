package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.user.entity.User;

public interface SavedPostRepository extends JpaRepository<SavedPost, Long>, SavedPostRepositoryCustom {
	List<SavedPost> findByUserOrderByPostCreatedAtDesc(User user);

	boolean existsByUserAndPost(User user, Post post);
}