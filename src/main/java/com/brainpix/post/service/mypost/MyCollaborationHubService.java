package com.brainpix.post.service.mypost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.PostCollaborationResponse;
import com.brainpix.post.repository.CollaborationHubRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCollaborationHubService {

	private final UserRepository userRepository;
	private final CollaborationHubRepository collaborationHubRepository;
	private final SavedPostRepository savedPostRepository;

	public Page<PostCollaborationResponse> findCollaborationPosts(long userId, Pageable pageable) {
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));
		return collaborationHubRepository.findByWriter(writer, pageable)
			.map(collaborationHub -> {
				Long saveCount = savedPostRepository.countByPostId(collaborationHub.getId());
				long totalQuantity = collaborationHub.getTotalQuantity();
				long occupiedQuantity = collaborationHub.getOccupiedQuantity();
				return PostCollaborationResponse.from(collaborationHub, saveCount, totalQuantity, occupiedQuantity);
			});
	}
}
