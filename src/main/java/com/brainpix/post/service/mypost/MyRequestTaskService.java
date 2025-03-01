package com.brainpix.post.service.mypost;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import com.brainpix.post.dto.PostRequestTaskResponse;
import com.brainpix.post.dto.mypostdto.MyRequestTaskDetailResponse;
import com.brainpix.post.dto.mypostdto.RequestTaskApplicationStatusResponse;
import com.brainpix.post.dto.mypostdto.RequestTaskCurrentMemberResponse;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.Individual;
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
				boolean isSavedPost = savedPostRepository.existsByUserIdAndPostId(userId, requestTask.getId());
				return PostRequestTaskResponse.from(requestTask, saveCount, isSavedPost);
			});
	}

	public MyRequestTaskDetailResponse getRequestTaskDetail(Long userId, Long postId) {
		RequestTask requestTask = requestTaskRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		requestTask.validateWriter(userId);

		//  지원 현황 조회
		List<RequestTaskApplicationStatusResponse> applicationStatus = requestTaskPurchasingRepository
			.findByRequestTaskRecruitmentInAndAcceptedIsNull(requestTask.getRecruitments()) // null 값만 가져오기
			.stream()
			.map(RequestTaskApplicationStatusResponse::from)
			.collect(Collectors.toList());

		// 현재 승인된 멤버 목록 조회 (역할별 그룹화)
		List<RequestTaskCurrentMemberResponse> currentMembers = requestTaskPurchasingRepository
			.findByRequestTaskRecruitmentInAndAcceptedTrue(requestTask.getRecruitments())
			.stream()
			.collect(Collectors.groupingBy(
				collection -> collection.getRequestTaskRecruitment().getDomain(),
				Collectors.mapping(collection -> {
					User buyer = collection.getBuyer();
					String userType = (buyer instanceof Individual) ? "개인" : "회사";
					Long acceptedMemberId = buyer.getId();
					return RequestTaskCurrentMemberResponse.AcceptedInfo.from(buyer.getIdentifier(), userType,
						acceptedMemberId);
				}, Collectors.toList())
			))
			.entrySet()
			.stream()
			.map(entry -> RequestTaskCurrentMemberResponse.from(entry.getKey(), entry.getValue()))
			.collect(Collectors.toList());

		return MyRequestTaskDetailResponse.from(requestTask, applicationStatus, currentMembers);
	}

	//  지원 수락
	@Transactional
	public void acceptApplication(Long userId, Long purchasingId) {
		RequestTaskPurchasing purchasing = requestTaskPurchasingRepository.findById(purchasingId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		purchasing.getRequestTaskRecruitment().getRequestTask().validateWriter(userId);

		// 이미 승인된 경우 예외 처리
		if (Boolean.TRUE.equals(purchasing.getAccepted())) {
			throw new BrainPixException(CommonErrorCode.METHOD_NOT_ALLOWED);
		}

		purchasing.approve();
	}

	//  지원 거절
	@Transactional
	public void rejectApplication(Long userId, Long purchasingId) {
		RequestTaskPurchasing purchasing = requestTaskPurchasingRepository.findById(purchasingId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		purchasing.getRequestTaskRecruitment().getRequestTask().validateWriter(userId);

		purchasing.reject();
	}

}
