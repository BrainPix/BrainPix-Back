package com.brainpix.post.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.SavedPostErrorCode;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.post.dto.SavedPostCollaborationResponse;
import com.brainpix.post.dto.SavedPostIdeaMarketResponse;
import com.brainpix.post.dto.SavedPostRequestTaskResponse;
import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
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

	public List<SavedPostRequestTaskResponse> findSavedRequestTasks(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.USER_NOT_FOUND.getMessage()));

		return savedPostRepository.findSavedRequestTasksByUser(user)
			.stream()
			.map(savedPost -> {
				RequestTask requestTask = (RequestTask)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(requestTask.getId());
				return SavedPostRequestTaskResponse.from(requestTask, saveCount);
			})
			.toList();
	}

	public List<SavedPostIdeaMarketResponse> findSavedIdeaMarkets(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.USER_NOT_FOUND.getMessage()));

		return savedPostRepository.findSavedIdeaMarketsByUser(user)
			.stream()
			.map(savedPost -> {
				IdeaMarket ideaMarket = (IdeaMarket)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());
				return SavedPostIdeaMarketResponse.from(ideaMarket, saveCount);
			})
			.toList();
	}

	public List<SavedPostCollaborationResponse> findSavedCollaborationHubs(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException(SavedPostErrorCode.USER_NOT_FOUND.getMessage()));

		return savedPostRepository.findSavedCollaborationHubsByUser(user)
			.stream()
			.map(savedPost -> {
				CollaborationHub collaborationHub = (CollaborationHub)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(collaborationHub.getId());

				// 모든 모집 데이터의 현재 인원과 모집 인원 합산
				List<CollaborationRecruitment> recruitments = collaborationHub.getCollaborations();
				long totalQuantity = recruitments.stream()
					.map(CollaborationRecruitment::getGathering)
					.filter(Objects::nonNull)
					.mapToLong(Gathering::getTotalQuantity)
					.sum();
				long occupiedQuantity = recruitments.stream()
					.map(CollaborationRecruitment::getGathering)
					.filter(Objects::nonNull)
					.mapToLong(Gathering::getOccupiedQuantity)
					.sum();

				return SavedPostCollaborationResponse.from(collaborationHub, saveCount, totalQuantity,
					occupiedQuantity);
			})
			.toList();
	}

}
