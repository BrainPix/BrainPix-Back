package com.brainpix.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.brainpix.post.entity.Comment;
import com.brainpix.post.entity.QComment;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
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

		List<Comment> result = queryFactory
			.selectFrom(comment)
			.leftJoin(comment.parentComment)	// comment 끼리 조인
			.where(
				comment.parentPost.id.eq(parentPostId)
			)
			.orderBy(
				 // 1. 부모는 자신의 ID를 기준, 자식은 부모 댓글의 ID를 기준으로 정렬.
				 // 결과적으로 부모 ID로 부모와 자식 그룹화
				Expressions.stringTemplate(
					"function('COALESCE', {0}, {1})",
					comment.parentComment.id, comment.id
				).asc(),
				// 2. 그룹화 된 후, 같은 그룹 내에서 부모와 자식 분리 (부모가 우선 나오도록)
				Expressions.stringTemplate(
					"case when {0} is null then 0 else 1 end",
					comment.parentComment.id
				).asc(),
				// 3. 자식 내에서 오래된 순으로 정렬
				comment.createdAt.asc()
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
