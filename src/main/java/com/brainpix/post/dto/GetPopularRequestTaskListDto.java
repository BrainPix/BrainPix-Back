package com.brainpix.post.dto;

import org.springframework.data.domain.Pageable;

import com.brainpix.post.entity.request_task.RequestTaskType;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetPopularRequestTaskListDto {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Request {
		@NotBlank(message = "요청 과제 타입은 필수입니다. (OPEN_IDEA, TECH_ZONE)")
		private String type;    // 요청 과제 타입
	}

	@Builder
	@Getter
	public static class Parameter {
		private RequestTaskType type;    // 요청 과제 타입
		private Pageable pageable;    // 페이징 기준
	}

	@Builder
	@Getter
	public static class RequestTaskDetail {
		private Long taskId;                // 게시글의 식별자 값
		private String auth;        // 공개 범위 (ALL, COMPANY)
		private String writerImageUrl;  // 작성자 프로필 이미지 경로
		private String writerName;      // 작성자 닉네임
		private String thumbnailImageUrl;          // 대표 이미지 경로
		private String title;               // 요청 과제 제목
		private Long deadline;                 // 남은 기간
		private String category;  // 게시글의 카테고리
		private Long saveCount;        // 저장수
		private Long viewCount;        // 조회수
	}
}
