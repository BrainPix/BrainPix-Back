package com.brainpix.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private Post parentPost;

	@ManyToOne
	private Comment parentComment;

	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> childComments = new ArrayList<>();

	private String content;

	@Builder
	public Comment(User writer, Post parentPost, Comment parentComment, String content) {
		this.writer = writer;
		this.parentPost = parentPost;
		this.parentComment = parentComment;
		this.content = content;
	}

	public Boolean validateWriter(User user) {
		return this.writer.equals(user);
	}
}
