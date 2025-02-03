package com.brainpix.post.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class GetRequestTaskDetailDto {

	@Builder
	@Getter
	public static class Parameter {
		private Long taskId;    // 요청 과제 ID
	}

	@Builder
	@Getter
	public static class Response {
		private Long taskId;                // 요청 과제 ID
		private String thumbnailImageUrl;   // 썸네일 이미지 URL
		private String category;            // 요청 과제 카테고리
		private String requestTaskType;  // OPEN_IDEA, TECH_ZONE
		private String auth;    // 공개 유형 (ALL, COMPANY)
		private String title;               // 요청 과제 제목
		private String content;         // 요청 과제 내용
		private Long deadline;                 // 남은 기간
		private Long viewCount;             // 요청 과제 조회수
		private Long saveCount;             // 요청 과제 저장수
		private LocalDate createdDate;         // 요청 과제 작성일 (YYYY/MM/DD)
		private Writer writer;          // 작성자
		private List<String> attachments; // 첨부 파일 목록
		private List<Recruitment> recruitments;    // 모집 단위
	}

	@Builder
	@Getter
	public static class Writer {
		private Long writerId;            // 작성자의 식별자 값
		private String name;              // 작성자 이름
		private String profileImageUrl;   // 작성자 프로필 이미지 URL
		private String role;              // 작성자 역할 (COMPANY, INDIVIDUAL)
		private String specialization; // 작성자의 분야 (IT_TECH, DESIGN, ...)
		private Long totalIdeas;       // 작성자가 등록한 요청 과제 수
		private Long totalCollaborations;     // 작성자가 협업한 경험 수
	}

	@Builder
	@Getter
	public static class Recruitment {
		private Long recruitmentId;        // 모집 단위 식별자
		private String domain;            // 모집 분야
		private Long occupiedQuantity;  // 현재 인원
		private Long totalQuantity;     // 전체 인원
		private Long price;                // 보수 금액
		private String paymentDuration;    // 보수 형태
	}
}
