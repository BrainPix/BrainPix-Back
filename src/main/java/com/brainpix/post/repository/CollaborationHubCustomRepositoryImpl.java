package com.brainpix.post.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.QSavedPost;
import com.brainpix.post.entity.collaboration_hub.QCollaborationHub;
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
public class CollaborationHubCustomRepositoryImpl implements CollaborationHubCustomRepository {

	private final JPAQueryFactory queryFactory;
	QCollaborationHub collaborationHub = QCollaborationHub.collaborationHub;
	QSavedPost savedPost = QSavedPost.savedPost;

	@Override
	public Page<Object[]> findCollaborationListWithSaveCount(String keyword, Specialization category,
		Boolean onlyCompany, SortType sortType, Pageable pageable) {

		// 검색 조건
		BooleanExpression where = Stream.of(
				PostBooleanExpression.COLLABORATION_EXCLUDE_PRIVATE.get(null),    // 1. 비공개 제외
				PostBooleanExpression.COLLABORATION_EXCLUDE_PAST.get(null),    // 2. 데드라인 지난 게시물 제외
				PostBooleanExpression.COLLABORATION_TITLE_CONTAINS.get(keyword),    // 3. 검색어 필터
				PostBooleanExpression.COLLABORATION_CATEGORY_EQ.get(category),    // 4. 카테고리 필터
				PostBooleanExpression.COLLABORATION_ONLY_COMPANY.get(onlyCompany)    // 5. 기업 공개만, 기업 공개 제외
			)
			.reduce(BooleanExpression::and)
			.orElse(null);

		// 정렬 조건
		// 정렬 조건 (기본 값은 최신순)
		OrderSpecifier<?> order = sortType != null ? sortType.getOrder() : SortType.COLLABORATION_NEWEST.getOrder();

		// 조회 결과
		List<Tuple> queryResult = queryFactory
			.select(collaborationHub, savedPost.count())
			.from(collaborationHub)
			.leftJoin(savedPost).on(collaborationHub.eq(savedPost.post))
			.where(where)
			.groupBy(collaborationHub)
			.orderBy(order)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(collaborationHub.count())
			.from(collaborationHub)
			.where(where);

		// 게시글-저장수를 쌍으로 저장
		List<Object[]> result = parsingResult(queryResult);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	private List<Object[]> parsingResult(List<Tuple> queryResult) {
		return queryResult.stream()
			.map(tuple -> {
				Object[] objects = new Object[2];
				objects[0] = tuple.get(collaborationHub);
				objects[1] = tuple.get(savedPost.count());
				return objects;
			})
			.toList();
	}
}
