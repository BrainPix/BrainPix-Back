package com.brainpix.post.enums;

import java.time.LocalDateTime;
import java.util.function.Function;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.collaboration_hub.QCollaborationHub;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.entity.idea_market.QIdeaMarket;
import com.brainpix.profile.entity.Specialization;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostBooleanExpression {

	// 아이디어 관련 검색 조건
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

	// 협업 광장 관련 검색 조건
	COLLABORATION_EXCLUDE_PRIVATE(obj -> QCollaborationHub.collaborationHub.postAuth.ne(PostAuth.ME)),
	COLLABORATION_EXCLUDE_PAST(obj -> QCollaborationHub.collaborationHub.deadline.after(LocalDateTime.now())),
	COLLABORATION_TITLE_CONTAINS(keyword -> keyword instanceof String ?
		QCollaborationHub.collaborationHub.title.contains((String)keyword) : null
	),
	COLLABORATION_CATEGORY_EQ(category -> category instanceof Specialization ?
		QCollaborationHub.collaborationHub.specialization.eq((Specialization)category) : null
	),
	COLLABORATION_ONLY_COMPANY(onlyCompany -> onlyCompany instanceof Boolean ?
		(Boolean)onlyCompany ?
			QCollaborationHub.collaborationHub.postAuth.eq(PostAuth.COMPANY) :
			QCollaborationHub.collaborationHub.postAuth.ne(PostAuth.COMPANY) : null
	);

	private final Function<Object, BooleanExpression> expressionFunction;

	public BooleanExpression get(Object value) {
		return expressionFunction.apply(value);
	}
}