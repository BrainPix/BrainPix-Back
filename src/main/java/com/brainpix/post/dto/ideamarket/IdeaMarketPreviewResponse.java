package com.brainpix.post.dto.ideamarket;

import java.time.LocalDateTime;

import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.profile.entity.Specialization;

public record IdeaMarketPreviewResponse(
	Long id,
	String writerName,
	String title,
	String imageUrl,
	Long price,
	LocalDateTime createdAt,
	Specialization specialization,
	IdeaMarketType ideaMarketType,
	Long saveCount,
	Long viewCount
) {
	public static IdeaMarketPreviewResponse from(IdeaMarket ideaMarket, Long saveCount) {
		return new IdeaMarketPreviewResponse(
			ideaMarket.getId(),
			ideaMarket.getWriter().getName(),
			ideaMarket.getTitle(),
			ideaMarket.getImageList().isEmpty() ? null : ideaMarket.getImageList().get(0),
			ideaMarket.getPrice().getPrice(),
			ideaMarket.getCreatedAt(),
			ideaMarket.getSpecialization(),
			ideaMarket.getIdeaMarketType(),
			saveCount,
			ideaMarket.getViewCount()
		);
	}
}
