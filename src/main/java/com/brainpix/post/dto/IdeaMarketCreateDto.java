package com.brainpix.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.profile.entity.Specialization;

import lombok.Data;

@Data
public class IdeaMarketCreateDto {
	private String title;
	private String content;
	private String category;
	private Specialization specialization;
	private IdeaMarketType ideaMarketType;
	private Boolean openMyProfile;
	private List<String> imageList;
	private List<String> attachmentFileList;
	private PostAuth postAuth;
	private Long price;
}
