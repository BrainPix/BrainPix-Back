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
public class AcceptedRequestTaskPurchasingDto {
	private Long purchasingId;            // RequestTaskPurchasing PK
	private String firstImage;            // 게시글 대표 이미지
	@Schema(type = "string", example = "yyyy-MM-dd ")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd ")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime postCreatedAt;  // 게시글 작성일
	private String postTitle;             // 게시글 제목
	private Specialization specialization;
	private String domain;                // 지원 파트

	// 게시물 작성자 정보 (이름 + 개인/회사)
	private String writerNickName;
	private String writerType;            // "개인" or "회사"
	private Long requestTaskId;
}
