package com.brainpix.post.dto;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.profile.entity.Specialization;

public record PostIdeaMarketResponse(
	Long ideaId,
	PostAuth auth,
	String writerImageUrl,
	String writerNickName,
	String thumbnailImageUrl,
	String title,
	Long price,
	Specialization specialization,
	Long saveCount,
	Long viewCount
) {
	public static PostIdeaMarketResponse from(IdeaMarket ideaMarket, Long saveCount) {
		return new PostIdeaMarketResponse(
			ideaMarket.getId(),
			ideaMarket.getPostAuth(),
			ideaMarket.getWriter().getProfileImage(),
			ideaMarket.getWriter().getNickName(),
			ideaMarket.getImageList().isEmpty() ? "thumbnail does not exist" : ideaMarket.getImageList().get(0),
			ideaMarket.getTitle(),
			ideaMarket.getPrice().getPrice(),
			ideaMarket.getSpecialization(),
			saveCount,
			ideaMarket.getViewCount()
		);
	}
}