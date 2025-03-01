package com.brainpix.joining.converter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.IdeaMarketPurchaseDto;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.user.entity.User;

@Component
public class IdeaMarketPurchasingConverter {

	public IdeaMarketPurchaseDto toPurchaseDto(IdeaMarketPurchasing purchasing) {
		Long purchasingId = purchasing.getId();
		LocalDateTime purchasedAt = purchasing.getCreatedAt();

		// 게시글
		IdeaMarket ideaMarket = purchasing.getIdeaMarket();
		Specialization category = ideaMarket.getSpecialization();
		String title = ideaMarket.getTitle();

		// 작성자
		User writer = ideaMarket.getWriter();
		String writerName = writer.getNickName();
		Long itemPrice = ideaMarket.getPrice().getPrice();

		return IdeaMarketPurchaseDto.builder()
			.purchasingId(purchasingId)
			.purchasedAt(purchasedAt)
			.specialization(category)
			.title(title)
			.writerNickName(writerName)
			.writerType(writer.getUserType())
			.middlePrice(itemPrice)
			.quantity(purchasing.getQuantity())
			.fee(purchasing.getVat())
			.finalPrice(purchasing.getPrice())
			.ideaMarketId(ideaMarket.getId())
			.build();
	}
}

