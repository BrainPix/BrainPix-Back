package com.brainpix.post.entity;

import com.brainpix.api.code.error.SavedPostErrorCode;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.repository.SavedPostRepository;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SavedPost extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user; // 저장한 사용자

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post; // 저장된 게시물

	@Builder
	public SavedPost(User user, Post post) {
		this.user = user;
		this.post = post;
	}

	public static void validateNotDuplicate(SavedPostRepository repository, User user, Post post) {
		if (repository.existsByUserAndPost(user, post)) {
			throw new IllegalStateException(SavedPostErrorCode.DUPLICATE_SAVED_POST.getMessage());
		}
	}
}