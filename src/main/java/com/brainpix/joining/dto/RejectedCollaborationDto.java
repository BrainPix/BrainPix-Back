package com.brainpix.joining.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectedCollaborationDto {
	private Long collectionGatheringId;   // CollectionGathering PK
	private String firstImage;            // 게시글 대표 이미지
	private LocalDateTime postCreatedAt;  // 게시글 작성일
	private String postTitle;             // 게시글 제목
	private String postCategory;          // "협업 광장 > 디자인" 등
	private String domain;                // 지원한 파트
}
