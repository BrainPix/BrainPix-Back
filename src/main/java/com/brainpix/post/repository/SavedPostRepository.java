package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.user.entity.User;

public interface SavedPostRepository extends JpaRepository<SavedPost, Long>, SavedPostRepositoryCustom {

	boolean existsByUserAndPost(User user, Post post);
}