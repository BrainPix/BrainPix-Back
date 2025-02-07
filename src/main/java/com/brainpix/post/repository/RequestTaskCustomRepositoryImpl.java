package com.brainpix.post.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.QSavedPost;
import com.brainpix.post.entity.request_task.QRequestTask;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.post.entity.request_task.RequestTaskType;
import com.brainpix.post.enums.PostBooleanExpression;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RequestTaskCustomRepositoryImpl implements RequestTaskCustomRepository {

	private final JPAQueryFactory queryFactory;
	QRequestTask requestTask = QRequestTask.requestTask;
	QSavedPost savedPost = QSavedPost.savedPost;

	@Override
	public Page<Object[]> findRequestTaskListWithSaveCount(Long userId, RequestTaskType requestTaskType, String keyword,
		Specialization category, Boolean onlyCompany, SortType sortType, Pageable pageable) {

		// 검색 조건
		BooleanExpression where = Stream.of(
				PostBooleanExpression.TASK_EXCLUDE_PRIVATE.get(null),    // 1. 비공개 제외
				PostBooleanExpression.TASK_EXCLUDE_PAST.get(null),    // 2. 데드라인 지난 게시물 제외
				PostBooleanExpression.TASK_TYPE_EQ.get(requestTaskType),    // 3. 요청 과제 필터링 (OPEN_IDEA, TECH_ZONE)
				PostBooleanExpression.TASK_TITLE_CONTAINS.get(keyword),    // 4. 검색어 필터
				PostBooleanExpression.TASK_CATEGORY_EQ.get(category),    // 5. 카테고리 필터
				PostBooleanExpression.TASK_ONLY_COMPANY.get(onlyCompany)    // 6. 기업 공개만, 기업 공개 제외
			)
			.reduce(BooleanExpression::and)
			.orElse(null);

		// 정렬 조건
		OrderSpecifier<?> order = savedPost.count().desc();

		// 저장 여부 판별을 위한 쿼리
		BooleanTemplate isSavedPost = Expressions.booleanTemplate(
			"CASE WHEN EXISTS (SELECT 1 FROM SavedPost sp WHERE sp.post.id = {0} AND sp.user.id = {1}) "
				+ "THEN TRUE ELSE FALSE END",
			requestTask.id, userId
		);

		// 조회 결과
		List<Tuple> queryResult = queryFactory
			.select(
				requestTask,
				savedPost.count(),
				isSavedPost
			)
			.from(requestTask)
			.leftJoin(savedPost).on(requestTask.eq(savedPost.post))
			.where(where)
			.groupBy(requestTask)
			.orderBy(order)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(requestTask.count())
			.from(requestTask)
			.where(where);

		// 게시글-저장수-저장여부를 쌍으로 저장
		List<Object[]> result = parsingResult(queryResult);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	@Override
	public Page<Object[]> findPopularRequestTaskListWithSaveCount(Long userId, RequestTaskType requestTaskType,
		Pageable pageable) {

		// 검색 조건
		BooleanExpression where = Stream.of(
				PostBooleanExpression.TASK_EXCLUDE_PRIVATE.get(null),    // 1. 비공개 제외
				PostBooleanExpression.TASK_EXCLUDE_PAST.get(null),    // 2. 데드라인 지난 게시물 제외
				PostBooleanExpression.TASK_TYPE_EQ.get(requestTaskType)    // 2. 요청 과제 필터링 (OPEN_IDEA, TECH_ZONE)
			)
			.reduce(BooleanExpression::and)
			.orElse(null);

		// 정렬 조건
		OrderSpecifier<?> order = savedPost.count().desc();

		// 저장 여부 판별을 위한 쿼리
		BooleanTemplate isSavedPost = Expressions.booleanTemplate(
			"CASE WHEN EXISTS (SELECT 1 FROM SavedPost sp WHERE sp.post.id = {0} AND sp.user.id = {1}) "
				+ "THEN TRUE ELSE FALSE END",
			requestTask.id, userId
		);

		// 조회 결과
		List<Tuple> queryResult = queryFactory
			.select(
				requestTask,
				savedPost.count(),
				isSavedPost
			)
			.from(requestTask)
			.leftJoin(savedPost).on(requestTask.eq(savedPost.post))
			.where(where)
			.groupBy(requestTask)
			.orderBy(order)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(requestTask.count())
			.from(requestTask)
			.where(where);

		// 게시글-저장수-저장여부를 쌍으로 저장
		List<Object[]> result = parsingResult(queryResult);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	private List<Object[]> parsingResult(List<Tuple> queryResult) {
		return queryResult.stream()
			.map(tuple -> {
				Object[] objects = new Object[3];
				objects[0] = tuple.get(0, RequestTask.class);
				objects[1] = tuple.get(1, Long.class);
				objects[2] = tuple.get(2, Boolean.class);
				return objects;
			})
			.toList();
	}
}
