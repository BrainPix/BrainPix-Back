package com.brainpix.joining.converter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.IdeaMarketPurchaseDto;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.user.entity.Individual;
import com.brainpix.user.entity.User;

@Component
public class IdeaMarketPurchasingConverter {

	public IdeaMarketPurchaseDto toPurchaseDto(IdeaMarketPurchasing purchasing) {
		Long purchasingId = purchasing.getId();
		LocalDateTime purchasedAt = purchasing.getCreatedAt();

		// 게시글
		IdeaMarket ideaMarket = purchasing.getIdeaMarket();
		String category = "아이디어마켓 > " + ideaMarket.getSpecialization();
		String title = ideaMarket.getTitle();

		// 작성자
		User writer = ideaMarket.getWriter();
		String writerName = writer.getName();
		String writerType = (writer instanceof Individual) ? "개인" : "회사";

		// 아이디어마켓 타입
		IdeaMarketType type = ideaMarket.getIdeaMarketType();

		// 수량 결정 (IDEA_SOLUTION → 1, MARKET_PLACE → price.totalQuantity)
		Price priceEntity = ideaMarket.getPrice();
		Long quantity = (type == IdeaMarketType.IDEA_SOLUTION)
			? 1L
			: priceEntity.getTotalQuantity();

		// 금액 계산
		long basePrice = priceEntity.getPrice() * quantity;
		long fee = (long)(basePrice * 0.01); // 1% 예시
		long finalPrice = basePrice + fee;

		return IdeaMarketPurchaseDto.builder()
			.purchasingId(purchasingId)
			.purchasedAt(purchasedAt)
			.category(category)
			.title(title)
			.writerName(writerName)
			.writerType(writerType)
			.quantity(quantity)
			.fee(fee)
			.finalPrice(finalPrice)
			.build();
	}
}

