package com.brainpix.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.post.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}
