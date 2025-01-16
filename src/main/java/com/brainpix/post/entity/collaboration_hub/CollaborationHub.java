package com.brainpix.post.entity.collaboration_hub;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.IdeaMarketAuth;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CollaborationHub extends Post {
	private LocalDateTime deadline;
	private String link;

	@Builder
	public CollaborationHub(User writer, String title, String content, String category, Boolean openMyProfile,
		Long viewCount, List<String> imageList, List<String> attachmentFileList, LocalDateTime deadline,
		String link, IdeaMarketAuth ideaMarketAuth) {
		super(writer, title, content, category, openMyProfile, viewCount, ideaMarketAuth, imageList,
			attachmentFileList);
		this.deadline = deadline;
		this.link = link;
	}
}
