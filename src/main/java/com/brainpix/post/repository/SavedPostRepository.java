package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.SavedPost;

public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {

	// 게시글의 저장 횟수 조회
	Long countByPostId(Long postId);
}
