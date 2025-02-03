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
public class AcceptedRequestTaskPurchasingDto {
	private Long purchasingId;            // RequestTaskPurchasing PK
	private String firstImage;            // 게시글 대표 이미지
	private LocalDateTime postCreatedAt;  // 게시글 작성일
	private String postTitle;             // 게시글 제목
	private String postCategory;          // "요청 과제 > 디자인"
	private String domain;                // 지원 파트

	// 게시물 작성자 정보 (이름 + 개인/회사)
	private String writerName;
	private String writerType;            // "개인" or "회사"
}