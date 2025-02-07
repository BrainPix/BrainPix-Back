package com.brainpix.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ApplyCollaborationDto {

	@NoArgsConstructor
	@Getter
	public static class Request {
		@NotNull(message = "지원 분야는 필수입니다.")
		Long collaborationRecruitmentId;    // 지원 분야 식별자
		@NotNull(message = "자기소개 및 포트폴리오 공개 여부를 결정해주세요.")
		Boolean isOpenProfile;    // 자기소개 및 포트폴리오 공개 여부
		String message;    // 추가 메시지
	}

	@Builder
	@Getter
	public static class Parameter {
		Long collaborationId;    // 요청 과제 식별자 ID
		Long userId;    // 유저 식별자 ID
		Long collaborationRecruitmentId;    // 지원 분야 식별자 ID
		Boolean isOpenProfile;    // 자기소개 및 포트폴리오 공개 여부
		String message;    // 추가 메시지
	}

	@Builder
	@Getter
	public static class Response {
		Long CollectionGatheringId;    // 모집 식별자 ID
	}
}
