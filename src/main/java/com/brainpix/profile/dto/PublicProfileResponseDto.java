package com.brainpix.profile.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicProfileResponseDto {
	private String userType; // 개인/기업
	private String nickname; // 닉네임
	private List<Specialization> specializations; // 전문 분야 (e.g., "디자인/기획")
	private String selfIntroduction; // 자기소개
	private List<PostPreviewDto> posts; // 게시물 리스트

	@Getter
	@Builder
	public static class PostPreviewDto {
		private Long postId;           // 게시글 PK
		private String openScope;      // 공개 범위
		private Specialization specialization;   // 카테고리 이름
		private String title;          // 제목
		private String writerName;     // 작성자 이름
		private Long savedCount;       // 스크랩/즐겨찾기 횟수
		private Long viewCount;        // 조회수
		@Schema(type = "string", example = "yyyy-MM-dd HH:mm")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
		private LocalDateTime deadline;// 마감일
		private String thumbnailImage; // 썸네일 이미지
		private String writerImageUrl; // 작성자 이미지 URL
		private Long price;            // (아이디어 마켓 전용)
		private Long currentMembers;   // (협업 광장 전용)
		private Long totalMembers;     // (협업 광장 전용)
		private boolean isSavedPost;

	}
}


