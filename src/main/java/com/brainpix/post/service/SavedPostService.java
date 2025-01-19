package com.brainpix.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.SavedPostErrorCode;
import com.brainpix.post.dto.SavedPostSimpleResponse;
import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.post.repository.PostRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.profile.repository.UserRepository;
import com.brainpix.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SavedPostService {

	private final SavedPostRepository savedPostRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public void savePost(long userId, long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.USER_NOT_FOUND.getMessage()));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.POST_NOT_FOUND.getMessage()));

		SavedPost.validateNotDuplicate(savedPostRepository, user, post);

		savedPostRepository.save(new SavedPost(user, post));
	}

	public List<SavedPostSimpleResponse> findSavedRequestTasks(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.USER_NOT_FOUND.getMessage()));

		return savedPostRepository.findSavedRequestTasksByUser(user)
			.stream()
			.map(savedPost -> new SavedPostSimpleResponse(savedPost.getPost().getFirstImage()))
			.toList();
	}

	public List<SavedPostSimpleResponse> findSavedIdeaMarkets(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.USER_NOT_FOUND.getMessage()));

		return savedPostRepository.findSavedIdeaMarketsByUser(user)
			.stream()
			.map(savedPost -> new SavedPostSimpleResponse(savedPost.getPost().getFirstImage()))
			.toList();
	}

	public List<SavedPostSimpleResponse> findSavedCollaborationHubs(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.USER_NOT_FOUND.getMessage()));

		return savedPostRepository.findSavedCollaborationHubsByUser(user)
			.stream()
			.map(savedPost -> new SavedPostSimpleResponse(savedPost.getPost().getFirstImage()))
			.toList();
	}

}
