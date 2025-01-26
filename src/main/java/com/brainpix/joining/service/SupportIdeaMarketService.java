package com.brainpix.joining.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.converter.IdeaMarketPurchasingConverter;
import com.brainpix.joining.dto.IdeaMarketPurchaseDto;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.repository.IdeaMarketPurchasingRepository;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupportIdeaMarketService {

	private final UserRepository userRepository;
	private final IdeaMarketPurchasingRepository purchasingRepository;
	private final IdeaMarketPurchasingConverter converter;

	@Transactional(readOnly = true)
	public List<IdeaMarketPurchaseDto> getMyPurchases(Long userId) {
		// 1) 유저
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 2) 구매 내역
		List<IdeaMarketPurchasing> list = purchasingRepository.findByBuyer(currentUser);

		// 3) 변환
		return list.stream()
			.map(converter::toPurchaseDto)
			.collect(Collectors.toList());
	}
}
