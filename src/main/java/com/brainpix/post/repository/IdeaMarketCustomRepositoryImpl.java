package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.QSavedPost;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.entity.idea_market.QIdeaMarket;
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

		// 조회 결과
		List<Tuple> queryResult = queryFactory
			.select(ideaMarket, savedPost.count())
			.from(ideaMarket)
			.leftJoin(savedPost).on(ideaMarket.eq(savedPost.post))
			.where(
				excludePrivate(),        // 1. 비공개 제외
				ideaMarketTypeEq(ideaMarketType),    // 2. 아이디어 필터링 (IDEA_SOLUTION, MARKET_PLACE)
				titleContains(keyword),        // 3. 검색어가 있는 경우
				categoryEq(category),    // 4. 카테고리가 있는 경우
				postAuthEq(onlyCompany)    // 5. 기업 공개만, 기업 공개 제외
			)
			.groupBy(ideaMarket)
			.orderBy(applySortType(sortType))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(ideaMarket.count())
			.from(ideaMarket)
			.leftJoin(savedPost).on(ideaMarket.eq(savedPost.post))
			.where(
				excludePrivate(),					// 1. 비공개 제외
				ideaMarketTypeEq(ideaMarketType),	// 2. 아이디어 필터링 (IDEA_SOLUTION, MARKET_PLACE)
				titleContains(keyword),				// 3. 검색어가 있는 경우
				categoryEq(category),				// 4. 카테고리가 있는 경우
				postAuthEq(onlyCompany)				// 5. 기업 공개만, 기업 공개 제외
			);

		// 게시글-저장수를 쌍으로 저장
		List<Object[]> result = parsingResult(queryResult);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	// 인기 아이디어 조회입니다.
	// ideaMarketType으로 아이디어 유형을 구분한 뒤,
	// 모든 아이디어에서 저장순으로 조회합니다.
	@Override
	public Page<Object[]> findPopularIdeaListWithSaveCount(IdeaMarketType ideaMarketType, Pageable pageable) {

		// 조회 결과
		List<Tuple> queryResult = queryFactory
			.select(ideaMarket, savedPost.count())
			.from(ideaMarket)
			.leftJoin(savedPost).on(ideaMarket.eq(savedPost.post))
			.where(
				excludePrivate(),        			// 1. 비공개 제외
				ideaMarketTypeEq(ideaMarketType)    // 2. 아이디어 필터링 (IDEA_SOLUTION, MARKET_PLACE)
			)
			.groupBy(ideaMarket)
			.orderBy(savedPost.count().desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(ideaMarket.count())
			.from(ideaMarket)
			.where(
				excludePrivate(),					// 1. 비공개 제외
				ideaMarketTypeEq(ideaMarketType)	// 2. 아이디어 필터링 (IDEA_SOLUTION, MARKET_PLACE)
			);

		// 게시글-저장수를 쌍으로 저장
		List<Object[]> result = parsingResult(queryResult);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	private BooleanExpression excludePrivate() {
		return ideaMarket.postAuth.ne(PostAuth.ME);
	}

	private BooleanExpression ideaMarketTypeEq(IdeaMarketType ideaMarketType) {
		return ideaMarketType != null ? ideaMarket.ideaMarketType.eq(ideaMarketType) : null;
	}

	private BooleanExpression titleContains(String keyword) {
		return keyword != null ? ideaMarket.title.contains(keyword) : null;
	}

	private BooleanExpression categoryEq(Specialization category) {
		return category != null ? ideaMarket.specialization.eq(category) : null;
	}

	private BooleanExpression postAuthEq(Boolean onlyCompany) {
		if (onlyCompany != null && onlyCompany)
			return ideaMarket.postAuth.eq(PostAuth.COMPANY);
		else
			return null;
	}

	private OrderSpecifier<?> applySortType(SortType sortType) {
		if (sortType != null) {
			if (sortType == SortType.NEWEST)
				return QIdeaMarket.ideaMarket.createdAt.desc();
			else if (sortType == SortType.OLDEST)
				return QIdeaMarket.ideaMarket.createdAt.asc();
			else if (sortType == SortType.POPULAR)
				return savedPost.count().desc();
			else if (sortType == SortType.LOWEST_PRICE)
				return QIdeaMarket.ideaMarket.price.price.asc();
			else
				return QIdeaMarket.ideaMarket.price.price.desc();
		} else {
			return QIdeaMarket.ideaMarket.createdAt.desc();
		}
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
