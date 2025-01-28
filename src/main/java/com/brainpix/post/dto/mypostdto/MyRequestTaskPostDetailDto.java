package com.brainpix.post.dto.mypostdto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRequestTaskPostDetailDto {
	private Long postId;
	private String title;                // 게시물 제목
	private String category;            // 카테고리
	private String dDay;                // 모집 D-Day
	private String thumbnailImage;      // 게시물 이미지

	private List<RequestTaskSupportInfo> supportStatus; // 지원 현황
	private List<RequestTaskCurrentMember> currentMembers; // 현재 인원
}