package com.brainpix.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.user.entity.User;

public interface SavedPostRepository extends JpaRepository<SavedPost, Long>, SavedPostRepositoryCustom {

	Long countByPostId(Long postId);

	Optional<SavedPost> findByUserAndPost(User user, Post post);

	boolean existsByUserAndPost(User user, Post post);
}