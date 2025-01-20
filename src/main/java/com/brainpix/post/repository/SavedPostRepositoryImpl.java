package com.brainpix.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.entity.QPost;
import com.brainpix.post.entity.QSavedPost;
import com.brainpix.post.entity.SavedPost;
import com.brainpix.post.entity.collaboration_hub.CollaborationHub;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.request_task.RequestTask;
import com.brainpix.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavedPostRepositoryImpl implements SavedPostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<SavedPost> findSavedRequestTasksByUser(User user, Pageable pageable) {
		List<SavedPost> savedPosts = queryFactory
			.selectFrom(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post).fetchJoin()
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(RequestTask.class)
			)
			.offset(pageable.getOffset()) // 페이지네이션 시작 위치
			.limit(pageable.getPageSize()) // 페이지 크기 설정
			.fetch();

		// 카운트 쿼리
		long total = Optional.ofNullable(queryFactory
			.select(QSavedPost.savedPost.count())
			.from(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post)
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(RequestTask.class)
			)
			.fetchOne()).orElse(0L);

		return new PageImpl<>(savedPosts, pageable, total);
	}

	@Override
	public Page<SavedPost> findSavedIdeaMarketsByUser(User user, Pageable pageable) {
		List<SavedPost> savedPosts = queryFactory
			.selectFrom(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post).fetchJoin()
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(IdeaMarket.class)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		long total = Optional.ofNullable(queryFactory
			.select(QSavedPost.savedPost.count())
			.from(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post)
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(IdeaMarket.class)
			)
			.fetchOne()).orElse(0L);

		return new PageImpl<>(savedPosts, pageable, total);
	}

	@Override
	public Page<SavedPost> findSavedCollaborationHubsByUser(User user, Pageable pageable) {
		List<SavedPost> savedPosts = queryFactory
			.selectFrom(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post).fetchJoin()
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(CollaborationHub.class)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		long total = Optional.ofNullable(queryFactory
			.select(QSavedPost.savedPost.count())
			.from(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post)
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(CollaborationHub.class)
			)
			.fetchOne()).orElse(0L);

		return new PageImpl<>(savedPosts, pageable, total);
	}
}
