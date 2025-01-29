package com.brainpix.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.PriceRepository;
import com.brainpix.post.converter.MyRequestTaskPostConverter;
import com.brainpix.post.dto.mypostdto.MyRequestTaskPostDetailDto;
import com.brainpix.post.dto.mypostdto.MyRequestTaskPostDto;
import com.brainpix.post.dto.mypostdto.RequestTaskCurrentMember;
import com.brainpix.post.dto.mypostdto.RequestTaskSupportInfo;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.post.repository.RequestTaskPurchasingRepository;
import com.brainpix.post.repository.RequestTaskRecruitmentRepository;
import com.brainpix.post.repository.RequestTaskRepository;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyRequestTaskPostManagementService {

	private final RequestTaskRepository requestTaskRepository;
	private final SavedPostRepository savedPostRepository;
	private final UserRepository userRepository;
	private final MyRequestTaskPostConverter converter;
	private final PriceRepository priceRepository;
	private final RequestTaskRecruitmentRepository requestTaskRecruitmentRepository;
	private final RequestTaskPurchasingRepository requestTaskPurchasingRepository;

	/**
	 * 내가 작성한 요청 과제 목록 조회
	 */
	@Transactional(readOnly = true)
	public Page<MyRequestTaskPostDto> getMyRequestTasks(Long userId, Pageable pageable) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		Pageable sortedPageable = PageRequest.of(
			pageable.getPageNumber(),
			pageable.getPageSize(),
			Sort.by(Sort.Direction.DESC, "createdAt") // 최신순 정렬
		);

		return requestTaskRepository.findByWriter(currentUser, pageable)
			.map(task -> {
				long savedCount = savedPostRepository.countByPostId(task.getId());
				return converter.toMyRequestTaskPostDto(task, savedCount);
			});
	}

	/**
	 * 내가 작성한 요청 과제 상세 조회
	 */
	@Transactional(readOnly = true)
	public MyRequestTaskPostDetailDto getRequestTaskDetail(Long userId, Long postId) {
		// 게시글 가져오기
		RequestTask task = requestTaskRepository.findById(postId)
			.orElseThrow(() -> new BrainPixException(PostErrorCode.REQUEST_TASK_NOT_FOUND));

		// 모집 정보 가져오기
		List<RequestTaskRecruitment> recruitments = requestTaskRecruitmentRepository.findByRequestTask(task);

		// 지원 현황 생성
		List<RequestTaskSupportInfo> supportList = recruitments.stream()
			.flatMap(recruitment -> requestTaskPurchasingRepository.findByRequestTaskRecruitment(recruitment)
				.stream()
				.filter(p -> p.getAccepted() == null) // 대기 중인 지원자
				.map(p -> converter.toSupportStatusDto(
					p.getBuyer(),
					recruitment.getDomain(),
					recruitment.getPrice().getOccupiedQuantity(),
					recruitment.getPrice().getTotalQuantity()
				))
			)
			.collect(Collectors.toList());

		// 현재 인원 생성
		List<RequestTaskCurrentMember> currentMembers = recruitments.stream()
			.flatMap(recruitment -> requestTaskPurchasingRepository.findByRequestTaskRecruitment(recruitment)
				.stream()
				.filter(p -> Boolean.TRUE.equals(p.getAccepted())) // 수락된 지원자
				.map(p -> converter.toCurrentMemberDto(
					p.getBuyer(),
					recruitment.getDomain(),
					recruitment.getPrice().getOccupiedQuantity()
				))
			)
			.collect(Collectors.toList());

		// DTO 반환
		return converter.toDetailDto(task, supportList, currentMembers);

	}

	//지원자 수락
	@Transactional
	public void acceptPurchasing(Long userId, Long purchasingId) {
		RequestTaskPurchasing purchasing = requestTaskPurchasingRepository.findById(purchasingId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 확인
		RequestTask task = purchasing.getRequestTaskRecruitment().getRequestTask();
		task.validateWriter(userId);

		// 수락 처리
		purchasing.accept();

		// 모집된 인원 업데이트
		Price price = purchasing.getRequestTaskRecruitment().getPrice();
		price.increaseOccupiedQuantity(1L);

		// 저장
		requestTaskPurchasingRepository.save(purchasing);
		priceRepository.save(price);
	}

	//지원자 거절
	@Transactional
	public void rejectPurchasing(Long userId, Long purchasingId) {
		RequestTaskPurchasing purchasing = requestTaskPurchasingRepository.findById(purchasingId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 작성자 확인
		RequestTask task = purchasing.getRequestTaskRecruitment().getRequestTask();
		task.validateWriter(userId);

		// 거절 처리
		purchasing.reject();

		// 모집된 인원 업데이트
		Price price = purchasing.getRequestTaskRecruitment().getPrice();

		// 저장
		requestTaskPurchasingRepository.save(purchasing);
		priceRepository.save(price);
	}
}
