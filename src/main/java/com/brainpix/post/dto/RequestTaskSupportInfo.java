package com.brainpix.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestTaskSupportInfo {
	private String userId;           // 지원자 아이디
	private String role;             // 지원 역할
	private String currentSlashTotal; // 현재 인원 / 모집 인원

}