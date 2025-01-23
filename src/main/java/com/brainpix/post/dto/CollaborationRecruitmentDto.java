package com.brainpix.post.dto;

import com.brainpix.joining.dto.GatheringDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationRecruitmentDto {
	private String domain;

	private GatheringDto gatheringDto;
}
