package com.brainpix.post.dto.mypostdto;

import java.util.List;

import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.profile.entity.Specialization;

public record MyIdeaMarketPostDetailResponse(
	Long postId,
	Specialization specialization,
	String title,
	Long price,
	String thumbnailImage,
	List<PurchaseInfoResponse> purchaseHistory // 구매 내역 리스트
) {
	public static MyIdeaMarketPostDetailResponse from(IdeaMarket ideaMarket,
		List<PurchaseInfoResponse> purchaseHistory) {
		return new MyIdeaMarketPostDetailResponse(
			ideaMarket.getId(),
			ideaMarket.getSpecialization(),
			ideaMarket.getTitle(),
			ideaMarket.getPrice().getPrice(),
			ideaMarket.getFirstImage(),
			purchaseHistory
		);
	}
}