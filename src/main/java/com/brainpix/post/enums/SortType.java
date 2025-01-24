package com.brainpix.post.enums;

import java.util.function.Supplier;

import com.brainpix.post.entity.QSavedPost;
import com.brainpix.post.entity.idea_market.QIdeaMarket;
import com.brainpix.post.entity.request_task.QRequestTask;
import com.querydsl.core.types.OrderSpecifier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SortType {

	// 아이디어 관련 정렬 조건
	IDEA_NEWEST(QIdeaMarket.ideaMarket.createdAt::desc),    // 최신 순
	IDEA_OLDEST(QIdeaMarket.ideaMarket.createdAt::asc),    // 오래된 순
	IDEA_POPULAR(QSavedPost.savedPost.count()::desc),    // 저장 순
	IDEA_HIGHEST_PRICE(QIdeaMarket.ideaMarket.price.price::desc),    // 높은 가격 순
	IDEA_LOWEST_PRICE(QIdeaMarket.ideaMarket.price.price::asc),    // 낮은 가격 순

	// 요청 과제 관련 정렬 조건
	REQUEST_NEWEST(QRequestTask.requestTask.createdAt::desc),    // 최신 순
	REQUEST_OLDEST(QRequestTask.requestTask.createdAt::asc),    // 오래된 순
	REQUEST_POPULAR(QSavedPost.savedPost.count()::desc);    // 저장 순

	private final Supplier<OrderSpecifier<?>> orderSpecifierSupplier;

	public OrderSpecifier<?> getOrder() {
		return orderSpecifierSupplier.get();
	}
}