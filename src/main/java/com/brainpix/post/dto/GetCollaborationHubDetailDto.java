package com.brainpix.post.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class GetCollaborationHubDetailDto {

	@Builder
	@Getter
	public static class Parameter {
		private Long collaborationId;    // 협업 게시글 ID
		private Long userId;    // 유저 ID
	}

	@Builder
	@Getter
	public static class Response {
		private Long collaborationId;    // 협업 게시글 ID
		private String thumbnailImageUrl;    // 썸네일 이미지 URL
		private String category;    // 협업 게시글 카테고리
		private String auth;    // 공개 유형 (ALL, COMPANY)
		private String title;    // 협업 게시글 제목
		private String content;    // 협업 게시글 내용
		private String link;    // 링크
		private Long deadline;    // 남은 기간
		private Long viewCount;    // 협업 게시글 조회수
		private Long saveCount;    // 협업 게시글 저장수
		private LocalDate createdDate;    // 협업 게시글 작성일 (YYYY/MM/DD)
		private Writer writer;    // 작성자
		private List<String> attachments;    // 첨부 파일 목록
		private List<Recruitment> recruitments;    // 모집 단위
		private List<OpenMember> openMembers;    // 개최 인원
		private Boolean openMyProfile;    // 프로필 공개 여부
		private Boolean isSavedPost;    // 게시글 저장 여부
		private Boolean isMyPost;    // 내 게시글인지 여부
	}

	@Builder
	@Getter
	public static class Writer {
		private Long writerId;            // 작성자의 식별자 값
		private String name;              // 작성자 이름
		private String profileImageUrl;   // 작성자 프로필 이미지 URL
		private String role;              // 작성자 역할 (COMPANY, INDIVIDUAL)
		private String specialization; // 작성자의 분야 (IT_TECH, DESIGN, ...)
		private Long totalIdeas;       // 작성자가 등록한 협업 게시글 수
		private Long totalCollaborations;     // 작성자가 협업한 경험 수
	}

	@Builder
	@Getter
	public static class Recruitment {
		private Long recruitmentId;        // 모집 단위 식별자
		private String domain;            // 모집 분야
		private Long occupiedQuantity;  // 현재 인원
		private Long totalQuantity;     // 전체 인원
	}

	@Builder
	@Getter
	public static class OpenMember {
		private Long userId;    // 유저 식별자 값
		private String name;    // 유저 이름
		private String domain;    // 유저 역할
		private Boolean openMyProfile;    // 포트폴리오 불러오기 여부
	}
}
