package com.brainpix.post.entity;

import java.util.List;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.user.entity.User;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public abstract class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User writer;

	private String title;
	private String content;
	private String category;
	private Boolean openMyProfile;
	private Long viewCount;

	@Enumerated(EnumType.STRING)
	private PostAuth postAuth;

	@ElementCollection
	private List<String> imageList;

	@ElementCollection
	private List<String> attachmentFileList;

	public Post(User writer, String title, String content, String category, Boolean openMyProfile, Long viewCount,
		PostAuth postAuth, List<String> imageList, List<String> attachmentFileList) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.category = category;
		this.openMyProfile = openMyProfile;
		this.viewCount = viewCount;
		this.postAuth = postAuth;
		this.imageList = imageList;
		this.attachmentFileList = attachmentFileList;
	}

	public void updateBaseFields(String title, String content, String category, Boolean openMyProfile,
		PostAuth postAuth, List<String> imageList, List<String> attachmentFileList) {
		this.title = title;
		this.content = content;
		this.category = category;
		this.openMyProfile = openMyProfile;
		this.postAuth = postAuth;
		this.imageList = imageList;
		this.attachmentFileList = attachmentFileList;
	}

}


