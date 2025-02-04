package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.PostIdeaMarketResponse;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyIdeaMarketService {

	private final IdeaMarketRepository ideaMarketRepository;
	private final SavedPostRepository savedPostRepository;
	private final UserRepository userRepository;

	public Page<PostIdeaMarketResponse> findIdeaMarketPosts(long userId, Pageable pageable) {
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return ideaMarketRepository.findByWriter(writer, pageable)
			.map(ideaMarket -> {
				Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());
				return PostIdeaMarketResponse.from(ideaMarket, saveCount);
			});
	}

}
