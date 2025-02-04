package com.brainpix.post.dto;

import com.brainpix.joining.dto.GatheringDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationRecruitmentDto {

	@NotBlank(message = "역할은 필수 입력 값입니다.")
	private String domain;

	private GatheringDto gatheringDto;
}
