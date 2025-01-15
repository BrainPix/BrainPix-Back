package com.brainpix.post.entity;

import java.util.List;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.user.entity.User;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class BasePost extends BaseTimeEntity {
	@ManyToOne
	private User writer;

	private String title;
	private String content;
	private String category;
	private Boolean openMyProfile;
	private Long viewCount;

	@Enumerated(EnumType.STRING)
	private IdeaMarketAuth ideaMarketAuth;

	@ElementCollection
	private List<String> imageList;

	@ElementCollection
	private List<String> attachmentFileList;

	public BasePost(User writer, String title, String content, String category, Boolean openMyProfile, Long viewCount,
		IdeaMarketAuth ideaMarketAuth, List<String> imageList, List<String> attachmentFileList) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.category = category;
		this.openMyProfile = openMyProfile;
		this.viewCount = viewCount;
		this.ideaMarketAuth = ideaMarketAuth;
		this.imageList = imageList;
		this.attachmentFileList = attachmentFileList;
	}

	public void updateBaseFields(String title, String content, String category, Boolean openMyProfile,
		IdeaMarketAuth ideaMarketAuth, List<String> imageList, List<String> attachmentFileList) {
		this.title = title;
		this.content = content;
		this.category = category;
		this.openMyProfile = openMyProfile;
		this.ideaMarketAuth = ideaMarketAuth;
		this.imageList = imageList;
		this.attachmentFileList = attachmentFileList;
	}

}


