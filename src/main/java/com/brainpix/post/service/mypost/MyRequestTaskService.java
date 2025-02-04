package com.brainpix.post.service.mypost;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import com.brainpix.post.dto.PostRequestTaskResponse;
import com.brainpix.post.dto.mypostdto.ApplicationStatusResponse;
import com.brainpix.post.dto.mypostdto.CurrentMemberResponse;
import com.brainpix.post.dto.mypostdto.MyRequestTaskDetailResponse;
import com.brainpix.post.entity.request_task.RequestTask;
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
	private final RequestTaskPurchasingRepository requestTaskPurchasingRepository;

	public Page<PostRequestTaskResponse> findReqeustTaskPosts(long userId, Pageable pageable) {
		User writer = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.USER_NOT_FOUND));

		return requestTaskRepository.findByWriter(writer, pageable)
			.map(requestTask -> {
				Long saveCount = savedPostRepository.countByPostId(requestTask.getId());
				return PostRequestTaskResponse.from(requestTask, saveCount);
			});
	}

	public MyRequestTaskDetailResponse getRequestTaskDetail(Long userId, Long postId) {
		RequestTask requestTask = requestTaskRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		requestTask.validateWriter(userId);

		//  지원 현황 조회
		List<ApplicationStatusResponse> applicationStatus = requestTaskPurchasingRepository
			.findByRequestTaskRecruitmentIn(requestTask.getRecruitments())
			.stream()
			.map(ApplicationStatusResponse::from)
			.collect(Collectors.toList());

		// 현재 승인된 멤버 목록 조회 (역할별 그룹화)
		List<CurrentMemberResponse> currentMembers = requestTaskPurchasingRepository
			.findByRequestTaskRecruitmentInAndAcceptedTrue(requestTask.getRecruitments())
			.stream()
			.collect(Collectors.groupingBy(
				purchasing -> purchasing.getRequestTaskRecruitment().getDomain(), //역할별 그룹화
				Collectors.mapping(purchasing -> purchasing.getBuyer().getId(), Collectors.toList()) // 승인된 멤버 ID 리스트
			))
			.entrySet()
			.stream()
			.map(entry -> CurrentMemberResponse.from(entry.getKey(), entry.getValue()))
			.collect(Collectors.toList());

		return MyRequestTaskDetailResponse.from(requestTask, applicationStatus, currentMembers);
	}

}
