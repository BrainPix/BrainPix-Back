package com.brainpix.post.entity;

import java.util.List;

import com.brainpix.user.entity.User;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class BasePost {
	@ManyToOne
	private User writer;

	private String title;
	private String contest;
	private String category;
	private Boolean openMyProfile;
	private Long viewCount;

	@ElementCollection
	private List<String> imageList;

	@ElementCollection
	private List<String> attachmentFileList;

	public BasePost(User writer, String title, String contest, String category, Boolean openMyProfile, Long viewCount,
		List<String> imageList, List<String> attachmentFileList) {
		this.writer = writer;
		this.title = title;
		this.contest = contest;
		this.category = category;
		this.openMyProfile = openMyProfile;
		this.viewCount = viewCount;
		this.imageList = imageList;
		this.attachmentFileList = attachmentFileList;
	}
}
