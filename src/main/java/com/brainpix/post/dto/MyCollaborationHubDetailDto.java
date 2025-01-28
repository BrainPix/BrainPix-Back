package com.brainpix.post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCollaborationHubDetailDto {
	private Long postId;
	private String title;
	private String category;
	private String dDay;
	private String thumbnailImage;
	private String link;
	private List<CollaborationSupportInfo> supportStatus;
	private List<CollaborationCurrentMemberInfo> currentMembers;
}