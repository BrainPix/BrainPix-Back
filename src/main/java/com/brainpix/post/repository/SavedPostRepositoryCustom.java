package com.brainpix.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.entity.SavedPost;
import com.brainpix.user.entity.User;

public interface SavedPostRepositoryCustom {
	Page<SavedPost> findSavedRequestTasksByUser(User user, Pageable pageable);

	Page<SavedPost> findSavedIdeaMarketsByUser(User user, Pageable pageable);

	Page<SavedPost> findSavedCollaborationHubsByUser(User user, Pageable pageable);
}