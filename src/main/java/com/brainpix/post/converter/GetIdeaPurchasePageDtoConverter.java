package com.brainpix.post.converter;

import com.brainpix.post.dto.GetIdeaPurchasePageDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.User;

public class GetIdeaPurchasePageDtoConverter {

	public static GetIdeaPurchasePageDto.Parameter toParameter(Long ideaId, Long userId) {

		return GetIdeaPurchasePageDto.Parameter.builder()
			.ideaId(ideaId)
			.userId(userId)
			.build();
	}

	public static GetIdeaPurchasePageDto.Response toResponse(IdeaMarket ideaMarket, User seller) {

		return GetIdeaPurchasePageDto.Response.builder()
			.ideaId(ideaMarket.getId())
			.thumbnailImageUrl(ideaMarket.getFirstImage())
			.price(ideaMarket.getPrice().getPrice())
			.title(ideaMarket.getTitle())
			.remainingQuantity(ideaMarket.getPrice().getRemainingQuantity())
			.sellerId(seller.getId())
			.name(seller.getName())
			.profileImageUrl(seller.getProfileImage())
			.build();
	}
}
