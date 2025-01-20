package com.brainpix.scrap.entity;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.entity.Post;
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
@Getter
@NoArgsConstructor
public class scrap extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Post scrapedPost;

	@ManyToOne
	private User scrapper;

	@Builder
	public scrap(Post scrapedPost, User scrapper) {
		this.scrapedPost = scrapedPost;
		this.scrapper = scrapper;
	}
}
