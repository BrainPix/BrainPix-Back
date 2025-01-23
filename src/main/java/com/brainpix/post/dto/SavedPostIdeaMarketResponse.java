package com.brainpix.post.dto;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.profile.entity.Specialization;

public record SavedPostIdeaMarketResponse(
	Long ideaId,
	PostAuth auth,
	String writerImageUrl,
	String writerName,
	String thumbnailImageUrl,
	String title,
	Long price,
	Specialization specialization,
	Long saveCount,
	Long viewCount
) {
	public static SavedPostIdeaMarketResponse from(IdeaMarket ideaMarket, Long saveCount) {
		return new SavedPostIdeaMarketResponse(
			ideaMarket.getId(),
			ideaMarket.getPostAuth(),
			ideaMarket.getWriter().getProfileImage(),
			ideaMarket.getWriter().getName(),
			ideaMarket.getImageList().isEmpty() ? "thumbnail does not exist" : ideaMarket.getImageList().get(0),
			ideaMarket.getTitle(),
			ideaMarket.getPrice().getPrice(),
			ideaMarket.getSpecialization(),
			saveCount,
			ideaMarket.getViewCount()
		);
	}
}