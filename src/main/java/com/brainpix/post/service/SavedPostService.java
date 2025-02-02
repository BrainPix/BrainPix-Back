package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.SavedPostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.SavedPostCollaborationResponse;
import com.brainpix.post.dto.SavedPostIdeaMarketResponse;
import com.brainpix.post.dto.SavedPostRequestTaskResponse;
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
	public boolean toggleSavedPost(long userId, long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(SavedPostErrorCode.POST_NOT_FOUND));

		// 1) 저장 여부 확인
		SavedPost existing = savedPostRepository.findByUserAndPost(user, post);

		if (existing != null) {
			// 이미 저장되어 있으면 삭제
			savedPostRepository.delete(existing);
			return false; // 반환값: 저장 해제됨(false)
		} else {
			// 저장되어 있지 않으면 새로 저장
			savedPostRepository.save(new SavedPost(user, post));
			return true; // 반환값: 저장됨(true)
		}
	}

	public Page<SavedPostRequestTaskResponse> findSavedRequestTasks(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return savedPostRepository.findSavedRequestTasksByUser(user, pageable)
			.map(savedPost -> {
				RequestTask requestTask = (RequestTask)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(requestTask.getId());
				return SavedPostRequestTaskResponse.from(requestTask, saveCount);
			});
	}

	public Page<SavedPostIdeaMarketResponse> findSavedIdeaMarkets(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return savedPostRepository.findSavedIdeaMarketsByUser(user, pageable)
			.map(savedPost -> {
				IdeaMarket ideaMarket = (IdeaMarket)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());
				return SavedPostIdeaMarketResponse.from(ideaMarket, saveCount);
			});
	}

	public Page<SavedPostCollaborationResponse> findSavedCollaborationHubs(long userId, Pageable pageable) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return savedPostRepository.findSavedCollaborationHubsByUser(user, pageable)
			.map(savedPost -> {
				CollaborationHub collaborationHub = (CollaborationHub)savedPost.getPost();
				Long saveCount = savedPostRepository.countByPostId(collaborationHub.getId());

				// 엔티티 메서드를 호출하여 모집 데이터의 합산 계산
				long totalQuantity = collaborationHub.getTotalQuantity();
				long occupiedQuantity = collaborationHub.getOccupiedQuantity();

				return SavedPostCollaborationResponse.from(collaborationHub, saveCount, totalQuantity,
					occupiedQuantity);
			});
	}

}
