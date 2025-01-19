package com.brainpix.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.PostAuth;

import lombok.Data;

@Data
public class CollaborationHubCreateDto {
	private String title;
	private String content;
	private String category;
	private Boolean openMyProfile;
	private List<String> imageList;
	private List<String> attachmentFileList;
	private LocalDateTime deadline;
	private String link;
	private PostAuth postAuth;
	private List<CollaborationRecruitmentDto> recruitments;
}
