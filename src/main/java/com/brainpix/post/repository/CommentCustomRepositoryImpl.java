package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.Comment;
import com.brainpix.post.entity.QComment;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

	private final JPAQueryFactory queryFactory;
	QComment comment = QComment.comment;

	@Override
	public Page<Comment> findByParentPostId(Long parentPostId, Pageable pageable) {

		BooleanBuilder builder = new BooleanBuilder();

		// 조회 쿼리 (Comment 테이블끼리 조인한뒤, 1. 부모 정렬, 2. 같은 그룹 내에서 자식끼리 정렬)
		List<Comment> result = queryFactory
			.selectFrom(comment)
			.leftJoin(comment.parentComment).on(comment.parentComment.eq(comment))
			.where(
				comment.parentPost.id.eq(parentPostId)
			)
			.orderBy(
				comment.createdAt.asc(),
				comment.parentComment.createdAt.asc()
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(comment.count())
			.from(comment)
			.where(
				comment.parentPost.id.eq(parentPostId)
			);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}
}
