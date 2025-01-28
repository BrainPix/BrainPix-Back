package com.brainpix.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCollaborationHubListDto {
	private Long postId;
	private String title;
	private String openScope;
	private String categoryName;
	private String writerName;
	private Long savedCount;
	private Long viewCount;
	private String dDay;
	private Long currentMembers;
	private Long totalMembers;
	private String thumbnailImage;
}
