package com.brainpix.joining.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMemberInfoDto {
	private String domain;       // 역할 (예: "디자이너")
	private Long occupied;       // 현재 인원수
	private Long total;          // 모집 정원
	private List<JoinerInfo> joiners; // 수락/초기멤버들의 아이디 목록

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class JoinerInfo {
		private String joinerID;
		private String userType;
	}
}