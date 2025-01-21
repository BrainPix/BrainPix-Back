package com.brainpix.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.joining.dto.IdeaMarketBuyerResponse;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.post.dto.ideamarket.IdeaMarketDetailResponse;
import com.brainpix.post.dto.ideamarket.IdeaMarketPreviewResponse;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.ideamarket.IdeaMarketPurchasingRepository;
import com.brainpix.post.repository.ideamarket.IdeaMarketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdeaMarketService {

	private final IdeaMarketRepository ideaMarketRepository;
	private final IdeaMarketPurchasingRepository purchasingRepository;
	private final SavedPostRepository savedPostRepository;

	/**
	 * 아이디어 마켓 미리보기
	 */
	@Transactional(readOnly = true)
	public List<IdeaMarketPreviewResponse> getIdeaMarketPreviews(Long userId) {
		List<IdeaMarket> ideaMarkets = ideaMarketRepository.findAllByWriterId(userId);

		return ideaMarkets.stream()
			.map(ideaMarket -> {
				Long saveCount = savedPostRepository.countByPostId(ideaMarket.getId());
				return IdeaMarketPreviewResponse.from(ideaMarket, saveCount);
			})
			.toList();
	}

	/**
	 * 아이디어 마켓 상세보기
	 */
	@Transactional(readOnly = true)
	public IdeaMarketDetailResponse getIdeaMarketDetail(Long ideaMarketId) {
		IdeaMarket ideaMarket = ideaMarketRepository.findById(ideaMarketId)
			.orElseThrow(() -> new IllegalArgumentException("IdeaMarket not found"));

		List<IdeaMarketPurchasing> purchasings = purchasingRepository.findAllByIdeaMarketId(ideaMarketId);

		List<IdeaMarketBuyerResponse> buyers = purchasings.stream()
			.map(IdeaMarketBuyerResponse::from)
			.toList();

		return IdeaMarketDetailResponse.builder()
			.id(ideaMarket.getId())
			.title(ideaMarket.getTitle())
			.content(ideaMarket.getContent())
			.imageUrl(
				ideaMarket.getImageList().isEmpty() ? null : ideaMarket.getImageList().get(0)
			)
			.createdAt(ideaMarket.getCreatedAt())
			.specialization(ideaMarket.getSpecialization())
			.ideaMarketType(ideaMarket.getIdeaMarketType())
			.price(ideaMarket.getPrice().getPrice())
			.buyers(buyers)
			.build();
	}
}

