package com.brainpix.post.enums;

import java.util.function.Supplier;

import com.brainpix.post.entity.QSavedPost;
import com.brainpix.post.entity.idea_market.QIdeaMarket;
import com.querydsl.core.types.OrderSpecifier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SortType {
	NEWEST(QIdeaMarket.ideaMarket.createdAt::desc),        // 최신 순
	OLDEST(QIdeaMarket.ideaMarket.createdAt::asc),        // 오래된 순
	POPULAR(QSavedPost.savedPost.count()::desc),	   // 저장 순
	HIGHST_PRICE(QIdeaMarket.ideaMarket.price.price::desc),	// 높은 가격 순
	LOWEST_PRICE(QIdeaMarket.ideaMarket.price.price::asc);   // 낮은 가격 순

	private final Supplier<OrderSpecifier<?>> orderSpecifierSupplier;

	public OrderSpecifier<?> getOrder() {
		return orderSpecifierSupplier.get();
	}
}