package com.brainpix.profile.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponseDto {
	private String name;                // 사용자 이름
	private String userType;            // 개인/기업
	private List<String> specializations; // 분야 (최대 2개)
	private long ideaCount;             // 아이디어 작성 횟수
	private long collaborationCount;    // 협업 경험 횟수
	private String selfIntroduction;    // 자기소개
}
