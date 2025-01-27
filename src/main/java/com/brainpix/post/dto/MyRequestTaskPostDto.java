package com.brainpix.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRequestTaskPostDto {
	private Long postId;           // 게시글 PK
	private String openScope;      // 공개 범위(기업 공개/개인 공개/전체 공개)
	private String categoryName;   // "요청 과제 > ..."
	private String title;
	private String writerName;     // 기업이면 user.name, 개인이면 user.nickName
	private Long savedCount;       // 스크랩/즐겨찾기 횟수
	private Long viewCount;        // 조회수
	private String dDay;           // D-3, D-17 등
	private String thumbnailImage; // 첫 이미지 (없으면 "thumbnail does not exist;")
}