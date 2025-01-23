package com.brainpix.post.enums;

import java.time.LocalDateTime;
import java.util.function.Function;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.entity.idea_market.QIdeaMarket;
import com.brainpix.post.entity.request_task.QRequestTask;
import com.brainpix.post.entity.request_task.RequestTaskType;
import com.brainpix.profile.entity.Specialization;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostBooleanExpression {

	// 아이디어 마켓 관련 조건
	IDEA_EXCLUDE_PRIVATE(obj -> QIdeaMarket.ideaMarket.postAuth.ne(PostAuth.ME)),
	IDEA_MARKET_TYPE_EQ(type -> type instanceof IdeaMarketType ?
		QIdeaMarket.ideaMarket.ideaMarketType.eq((IdeaMarketType)type) : null
	),
	IDEA_TITLE_CONTAINS(keyword -> keyword instanceof String ?
		QIdeaMarket.ideaMarket.title.contains((String)keyword) : null
	),
	IDEA_CATEGORY_EQ(category -> category instanceof Specialization ?
		QIdeaMarket.ideaMarket.specialization.eq((Specialization)category) : null
	),
	IDEA_ONLY_COMPANY(onlyCompany -> onlyCompany instanceof Boolean ?
		(Boolean)onlyCompany ?
			QIdeaMarket.ideaMarket.postAuth.eq(PostAuth.COMPANY) :
			QIdeaMarket.ideaMarket.postAuth.ne(PostAuth.COMPANY) : null
	),

	// 요청 과제 관련 조건
	TASK_EXCLUDE_PRIVATE(obj -> QRequestTask.requestTask.postAuth.ne(PostAuth.ME)),
	TASK_EXCLUDE_PAST(obj -> QRequestTask.requestTask.deadline.after(LocalDateTime.now())),
	TASK_TYPE_EQ(type -> type instanceof RequestTaskType ?
		QRequestTask.requestTask.requestTaskType.eq((RequestTaskType)type) : null
	),
	TASK_TITLE_CONTAINS(keyword -> keyword instanceof String ?
		QRequestTask.requestTask.title.contains((String)keyword) : null
	),
	TASK_CATEGORY_EQ(category -> category instanceof Specialization ?
		QRequestTask.requestTask.specialization.eq((Specialization)category) : null
	),
	TASK_ONLY_COMPANY(onlyCompany -> onlyCompany instanceof Boolean ?
		(Boolean)onlyCompany ?
			QRequestTask.requestTask.postAuth.eq(PostAuth.COMPANY) :
			QRequestTask.requestTask.postAuth.ne(PostAuth.COMPANY) : null
	);

	private final Function<Object, BooleanExpression> expressionFunction;

	public BooleanExpression get(Object value) {
		return expressionFunction.apply(value);
	}
}