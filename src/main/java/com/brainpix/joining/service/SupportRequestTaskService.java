package com.brainpix.joining.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.joining.converter.RequestTaskPurchasingConverter;
import com.brainpix.joining.dto.AcceptedRequestTaskPurchasingDto;
import com.brainpix.joining.dto.RejectedRequestTaskPurchasingDto;
import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.repository.RequestTaskPurchasingRepository;
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
	public List<RejectedRequestTaskPurchasingDto> getRejectedList(Long userId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

		// "accepted = false" 목록 가져오기
		List<RequestTaskPurchasing> rejectedList =
			purchasingRepository.findByBuyerAndAcceptedIsFalse(currentUser);

		// 변환 로직 -> converter 사용
		return rejectedList.stream()
			.map(converter::toRejectedDto) // 메서드 레퍼런스
			.collect(Collectors.toList());
	}

	/**
	 * 수락 목록 조회
	 */
	@Transactional(readOnly = true)
	public List<AcceptedRequestTaskPurchasingDto> getAcceptedList(Long userId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

		List<RequestTaskPurchasing> acceptedList =
			purchasingRepository.findByBuyerAndAcceptedIsTrue(currentUser);

		return acceptedList.stream()
			.map(converter::toAcceptedDto)
			.collect(Collectors.toList());
	}

	/**
	 * 거절된 지원 삭제(물리적으로 DB에서 제거)
	 */
	@Transactional
	public void deleteRejectedPurchasing(Long userId, Long purchasingId) {
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

		RequestTaskPurchasing purchasing = purchasingRepository.findById(purchasingId)
			.orElseThrow(() -> new RuntimeException("지원 내역이 존재하지 않습니다."));

		// 본인이 지원한 항목인지 확인
		if (!purchasing.getBuyer().equals(currentUser)) {
			throw new RuntimeException("본인이 지원한 항목이 아닙니다.");
		}

		// 거절 상태인지 확인
		if (Boolean.TRUE.equals(purchasing.getAccepted())) {
			throw new RuntimeException("거절 상태가 아닌 항목은 삭제할 수 없습니다.");
		}

		// DB에서 실제 삭제
		purchasingRepository.delete(purchasing);
	}
}
