package com.brainpix.post.repository;

import java.util.List;

import com.brainpix.post.entity.SavedPost;
import com.brainpix.user.entity.User;

public interface SavedPostRepositoryCustom {
	List<SavedPost> findSavedRequestTasksByUser(User user);

	List<SavedPost> findSavedIdeaMarketsByUser(User user);

	List<SavedPost> findSavedCollaborationHubsByUser(User user);
}