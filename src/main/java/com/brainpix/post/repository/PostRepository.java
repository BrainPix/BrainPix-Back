package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brainpix.post.entity.Post;
import com.brainpix.user.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findByWriter(User writer, Pageable pageable);

	@Modifying
	@Query("update Post p set p.viewCount = p.viewCount + :viewCount where p.id = :id")
	Integer updateViewCount(@Param("id") Long postId, @Param("viewCount") Long viewCount);
}