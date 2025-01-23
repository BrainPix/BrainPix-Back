package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.entity.Comment;

public interface CommentCustomRepository {

	// 댓글과 대댓글을 모두 포함한 뒤 페이징을 처리합니다.
	Page<Comment> findByParentPostId(Long parentPostId, Pageable pageable);
}
