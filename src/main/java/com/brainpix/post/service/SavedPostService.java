package com.brainpix.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 userId입니다."));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId입니다."));

		// 중복 저장 방지
		if (savedPostRepository.existsByUserAndPost(user, post)) {
			throw new IllegalStateException("이미 저장된 게시물입니다.");
		}

		savedPostRepository.save(new SavedPost(user, post));
	}

	public List<SavedPostSimpleResponse> findSavedRequestTasks(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 userId입니다."));

		return savedPostRepository.findSavedRequestTasksByUser(user)
			.stream()
			.map(savedPost -> new SavedPostSimpleResponse(savedPost.getPost().getFirstImage()))
			.toList();
	}

	public List<SavedPostSimpleResponse> findSavedIdeaMarkets(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 userId입니다."));

		return savedPostRepository.findSavedIdeaMarketsByUser(user)
			.stream()
			.map(savedPost -> new SavedPostSimpleResponse(savedPost.getPost().getFirstImage()))
			.toList();
	}

	public List<SavedPostSimpleResponse> findSavedCollaborationHubs(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 userId입니다."));

		return savedPostRepository.findSavedCollaborationHubsByUser(user)
			.stream()
			.map(savedPost -> new SavedPostSimpleResponse(savedPost.getPost().getFirstImage()))
			.toList();
	}

}
