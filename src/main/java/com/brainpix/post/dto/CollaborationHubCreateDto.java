package com.brainpix.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationHubCreateDto extends PostDto {

	@Future(message = "마감일은 현재 날짜보다 미래여야 합니다.")
	@NotNull(message = "마감일은 필수입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;

	private String link;

	@NotEmpty(message = "모집 정보는 최소 하나 이상 필요합니다.")
	@Valid
	private List<CollaborationRecruitmentDto> recruitments;

	@NotEmpty(message = "개최 인원 정보는 최소 한 명 이상 등록해야 합니다.")
	@Valid
	private List<CollaborationHubInitialMemberDto> initialMembers;
}
