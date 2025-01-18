package com.brainpix.post.repository;

import java.util.List;

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
	public List<SavedPost> findSavedRequestTasksByUser(User user) {
		return queryFactory
			.selectFrom(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post)
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(RequestTask.class)
			)
			.fetch();
	}

	@Override
	public List<SavedPost> findSavedIdeaMarketsByUser(User user) {
		return queryFactory
			.selectFrom(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post)
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(IdeaMarket.class)
			)
			.fetch();
	}

	@Override
	public List<SavedPost> findSavedCollaborationHubsByUser(User user) {
		return queryFactory
			.selectFrom(QSavedPost.savedPost)
			.join(QSavedPost.savedPost.post, QPost.post)
			.where(
				QSavedPost.savedPost.user.eq(user),
				QPost.post.instanceOf(CollaborationHub.class)
			)
			.fetch();
	}
}
