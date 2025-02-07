package com.brainpix.joining.dto;

import java.time.LocalDateTime;
import java.util.List;

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
public class AcceptedCollaborationDto {

	private Long collectionGatheringId;   // CollectionGathering PK
	private String firstImage;            // 게시글 대표 이미지
	@Schema(type = "string", example = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime postCreatedAt;  // 게시글 작성일
	private String postTitle;             // 게시글 제목
	private Specialization specialization;
	private String domain;                // 내가 지원한 파트 (ex: "PM")

	// 게시글 관리자
	private String writerName;            // 작성자 이름 (ex: "SEO YEON")
	private String writerType;            // "개인" or "회사"

	// 팀원 정보 (모든 도메인에 대한 현재인원/총인원 + 멤버)
	private List<TeamMemberInfoDto> teamInfoList;
	private Long collaborationId;
}
