package com.brainpix.joining.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.PurchasingErrorCode;
import com.brainpix.api.code.error.RequestTaskErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.converter.RequestTaskPurchasingConverter;
import com.brainpix.joining.dto.AcceptedRequestTaskPurchasingDto;
import com.brainpix.joining.dto.RejectedRequestTaskPurchasingDto;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
import com.brainpix.joining.util.PageableUtils;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupportRequestTaskService {

	private final RequestTaskPurchasingRepository purchasingRepository;
	private final UserRepository userRepository;
	private final RequestTaskPurchasingConverter converter;

	/**
	 * 거절 목록 조회
	 */
	@Transactional(readOnly = true)
	public CommonPageResponse<RejectedRequestTaskPurchasingDto> getRejectedList(Long userId, Pageable pageable) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.USER_NOT_FOUND));

		Pageable sortedPageable = PageableUtils.withSort(pageable, "createdAt", Sort.Direction.DESC);

		Page<RequestTaskPurchasing> rejectedPage =
			purchasingRepository.findByBuyerAndAcceptedIsFalse(currentUser, sortedPageable);

		Page<RejectedRequestTaskPurchasingDto> dtoPage = rejectedPage.map(converter::toRejectedDto);
		return CommonPageResponse.of(dtoPage);
	}

	@Transactional(readOnly = true)
	public CommonPageResponse<AcceptedRequestTaskPurchasingDto> getAcceptedList(Long userId, Pageable pageable) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.USER_NOT_FOUND));

		Pageable sortedPageable = PageableUtils.withSort(pageable, "createdAt", Sort.Direction.DESC);

		Page<RequestTaskPurchasing> acceptedPage =
			purchasingRepository.findByBuyerAndAcceptedIsTrue(currentUser, sortedPageable);

		Page<AcceptedRequestTaskPurchasingDto> dtoPage = acceptedPage.map(converter::toAcceptedDto);
		return CommonPageResponse.of(dtoPage);
	}

	/**
	 * 거절된 지원 삭제(물리적으로 DB에서 제거)
	 */
	@Transactional
	public void deleteRejectedPurchasing(Long userId, Long purchasingId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(RequestTaskErrorCode.USER_NOT_FOUND));

		RequestTaskPurchasing purchasing = purchasingRepository.findById(purchasingId)
			.orElseThrow(() -> new BrainPixException(PurchasingErrorCode.COLLECTION_NOT_FOUND));

		purchasing.validateBuyer(currentUser);
		purchasing.validateRejectedStatus();

		// DB에서 실제 삭제
		purchasingRepository.delete(purchasing);
	}

}
