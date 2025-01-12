package com.brainpix.post.entity.request_task;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User writer;

	@ManyToOne
	private RequestTask parentRequestTask;

	@ManyToOne
	private Comment parentComment;

	private String content;

	@Builder
	public Comment(User writer, RequestTask parentRequestTask, Comment parentComment, String content) {
		this.writer = writer;
		this.parentRequestTask = parentRequestTask;
		this.parentComment = parentComment;
		this.content = content;
	}
}
