package com.brainpix.joining.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.brainpix.profile.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(type = "string", example = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime postCreatedAt;  // 게시글 작성일
	private String postTitle;             // 게시글 제목
	private Specialization specialization;
	private String domain;                // 지원한 파트
	private Long collaborationId;
}
