package com.brainpix.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.PostRequestTaskResponse;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyRequestTaskService {

	private final UserRepository userRepository;
	private final RequestTaskRepository requestTaskRepository;
	private final SavedPostRepository savedPostRepository;

	public Page<PostRequestTaskResponse> findReqeustTaskPosts(long userId, Pageable pageable) {
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return requestTaskRepository.findByWriter(writer, pageable)
			.map(requestTask -> {
				Long saveCount = savedPostRepository.countByPostId(requestTask.getId());
				return PostRequestTaskResponse.from(requestTask, saveCount);
			});
	}
}
