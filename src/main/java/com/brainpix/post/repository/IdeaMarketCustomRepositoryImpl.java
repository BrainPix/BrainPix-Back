package com.brainpix.post.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.QSavedPost;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.entity.idea_market.QIdeaMarket;
import com.brainpix.post.enums.PostBooleanExpression;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class IdeaMarketCustomRepositoryImpl implements IdeaMarketCustomRepository {

	private final JPAQueryFactory queryFactory;
	QIdeaMarket ideaMarket = QIdeaMarket.ideaMarket;
	QSavedPost savedPost = QSavedPost.savedPost;

	// 전체 조회 기능입니다.
	// ideaMarketType으로 아이디어 유형을 구분한 뒤,
	// 검색 조건을 동적으로 적용하여 아이디어 목록을 조회합니다.
	@Override
	public Page<Object[]> findIdeaListWithSaveCount(IdeaMarketType ideaMarketType, String keyword, Specialization category,
		Boolean onlyCompany, SortType sortType, Pageable pageable) {

		// 검색 조건
		BooleanExpression where = Stream.of(
				PostBooleanExpression.EXCLUDE_PRIVATE.get(null),                // 1. 비공개 제외
				PostBooleanExpression.IDEA_MARKET_TYPE_EQ.get(ideaMarketType),  // 2. 아이디어 필터링 (IDEA_SOLUTION, MARKET_PLACE)
				PostBooleanExpression.TITLE_CONTAINS.get(keyword),              // 3. 검색어 필터
				PostBooleanExpression.CATEGORY_EQ.get(category),                // 4. 카테고리 필터
				PostBooleanExpression.ONLY_COMPANY.get(onlyCompany)             // 5. 기업 공개만, 기업 공개 제외
			)
			.reduce(BooleanExpression::and)
			.orElse(null);

		// 정렬 조건 (기본 값은 최신순)
		OrderSpecifier<?> order = sortType != null ? sortType.getOrder() : SortType.NEWEST.getOrder();

		// 조회 결과
		List<Tuple> queryResult = queryFactory
			.select(ideaMarket, savedPost.count())
			.from(ideaMarket)
			.leftJoin(savedPost).on(ideaMarket.eq(savedPost.post))
			.where(where)
			.groupBy(ideaMarket)
			.orderBy(order)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(ideaMarket.count())
			.from(ideaMarket)
			.leftJoin(savedPost).on(ideaMarket.eq(savedPost.post))
			.where(where);

		// 게시글-저장수를 쌍으로 저장
		List<Object[]> result = parsingResult(queryResult);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	// 인기 아이디어 조회입니다.
	// ideaMarketType으로 아이디어 유형을 구분한 뒤,
	// 모든 아이디어에서 저장순으로 조회합니다.
	@Override
	public Page<Object[]> findPopularIdeaListWithSaveCount(IdeaMarketType ideaMarketType, Pageable pageable) {

		// 검색 조건
		BooleanExpression where = Stream.of(
				PostBooleanExpression.EXCLUDE_PRIVATE.get(null),                // 1. 비공개 제외
				PostBooleanExpression.IDEA_MARKET_TYPE_EQ.get(ideaMarketType)  // 2. 아이디어 필터링 (IDEA_SOLUTION, MARKET_PLACE)
			)
			.reduce(BooleanExpression::and)
			.orElse(null);

		// 정렬 조건
		OrderSpecifier<?> order = savedPost.count().desc();

		// 조회 결과
		List<Tuple> queryResult = queryFactory
			.select(ideaMarket, savedPost.count())
			.from(ideaMarket)
			.leftJoin(savedPost).on(ideaMarket.eq(savedPost.post))
			.where(where)
			.groupBy(ideaMarket)
			.orderBy(order)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(ideaMarket.count())
			.from(ideaMarket)
			.where(where);

		// 게시글-저장수를 쌍으로 저장
		List<Object[]> result = parsingResult(queryResult);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	private List<Object[]> parsingResult(List<Tuple> queryResult) {
		return queryResult.stream()
			.map(tuple -> {
				Object[] objects = new Object[2];
				objects[0] = tuple.get(ideaMarket);
				objects[1] = tuple.get(savedPost.count());
				return objects;
			})
			.toList();
	}
}
