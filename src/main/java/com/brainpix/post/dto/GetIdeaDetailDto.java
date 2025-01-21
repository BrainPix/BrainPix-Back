package com.brainpix.post.dto;

import java.time.LocalDate;
import java.util.List;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.profile.entity.Specialization;

import lombok.Builder;
import lombok.Getter;

public class GetIdeaDetailDto {

	@Builder
	@Getter
	public static class Parameter {
		private Long ideaId;	// 아이디어 ID
	}

	@Builder
	@Getter
	public static class Response {
		private Long ideaId;                // 아이디어 ID
		private String thumbnailImageUrl;   // 썸네일 이미지 URL
		private String category;            // 아이디어 카테고리
		private String ideaMarketType;  // 아이디어 솔루션 / 마켓 플레이스
		private String auth;  // 아이디어 공개 유형 (ALL, COMPANY)
		private String title;               // 아이디어 제목
		private String content;         // 아이디어 내용
		private Long price;                 // 아이디어 가격
		private Long viewCount;             // 아이디어 조회수
		private Long saveCount;             // 아이디어 저장수
		private Long totalQuantity;            // 전체 수량
		private Long occupiedQuantity;        // 팔린 수량
		private LocalDate createdDate;         // 아이디어 작성일 (YYYY/MM/DD)
		private Writer writer;                // 작성자
		private List<String> attachments; // 첨부 파일 목록
	}

	@Builder
	@Getter
	public static class Writer {
		private Long writerId;            // 작성자의 식별자 값
		private String name;              // 작성자 이름
		private String profileImageUrl;   // 작성자 프로필 이미지 URL
		private String role;              // 작성자 역할 (COMPANY, INDIVIDUAL)
		private String specialization; // 작성자의 분야 (IT_TECH, DESIGN, ...)
		private Long totalIdeas;       // 작성자가 등록한 아이디어 수
		private Long totalCollaborations;     // 작성자가 협업한 경험 수
	}
}
