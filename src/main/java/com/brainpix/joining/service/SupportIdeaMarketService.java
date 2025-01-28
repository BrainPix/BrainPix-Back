package com.brainpix.joining.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.CommonPageResponse;
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
	public CommonPageResponse<IdeaMarketPurchaseDto> getMyPurchases(Long userId, Pageable pageable) {
		// 유저
		User currentUser = userRepository.findById(userId)
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 구매내역
		Page<IdeaMarketPurchasing> page = purchasingRepository.findByBuyer(currentUser, pageable);

		Page<IdeaMarketPurchaseDto> dtoPage = page.map(converter::toPurchaseDto);

		return CommonPageResponse.of(dtoPage);
	}

}
