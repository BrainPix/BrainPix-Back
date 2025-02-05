package com.brainpix.post.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.PostCollaborationResponse;
import com.brainpix.post.dto.PostIdeaMarketResponse;
import com.brainpix.post.dto.PostRequestTaskResponse;
import com.brainpix.post.dto.SavePostResponseDto;
import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.PostRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SavedPostService {

	private final SavedPostRepository savedPostRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public SavePostResponseDto savePost(long userId, long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		Optional<SavedPost> savedPost = savedPostRepository.findByUserAndPost(user, post);

		boolean isSaved;
		if (savedPost.isEmpty()) {
			savedPostRepository.save(new SavedPost(user, post));    // 즐겨찾기 등록
			isSaved = true;
		} else {
			savedPostRepository.delete(savedPost.get());    // 즐겨찾기 해제
			isSaved = false;
		}

		return SavePostResponseDto.from(isSaved);
	}

	public Page<PostRequestTaskResponse> findSavedRequestTasks(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return savedPostRepository.findSavedRequestTasksByUser(user, pageable)
			.map(savedPost -> {
				RequestTask requestTask = (RequestTask)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(requestTask.getId());
				return PostRequestTaskResponse.from(requestTask, saveCount);
			});
	}

	public Page<PostIdeaMarketResponse> findSavedIdeaMarkets(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return savedPostRepository.findSavedIdeaMarketsByUser(user, pageable)
			.map(savedPost -> {
				IdeaMarket ideaMarket = (IdeaMarket)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());
				return PostIdeaMarketResponse.from(ideaMarket, saveCount);
			});
	}

	public Page<PostCollaborationResponse> findSavedCollaborationHubs(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return savedPostRepository.findSavedCollaborationHubsByUser(user, pageable)
			.map(savedPost -> {
				CollaborationHub collaborationHub = (CollaborationHub)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(collaborationHub.getId());

				// 엔티티 메서드를 호출하여 모집 데이터의 합산 계산
				long totalQuantity = collaborationHub.getTotalQuantity();
				long occupiedQuantity = collaborationHub.getOccupiedQuantity();

				return PostCollaborationResponse.from(collaborationHub, saveCount, totalQuantity,
					occupiedQuantity);
			});
	}
}
