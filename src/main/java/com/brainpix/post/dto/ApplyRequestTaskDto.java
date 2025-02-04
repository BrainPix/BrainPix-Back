package com.brainpix.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ApplyRequestTaskDto {

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Request {
		@NotNull(message = "지원 분야는 필수입니다.")
		Long requestRecruitmentId;    // 지원 분야 식별자
		@NotNull(message = "자기소개 및 포트폴리오 공개 여부를 결정해주세요.")
		Boolean isOpenProfile;    // 자기소개 및 포트폴리오 공개 여부
		@NotNull(message = "메시지에 null 값은 허용되지 않습니다.")
		String message;    // 추가 메시지
	}

	@Builder
	@Getter
	public static class Parameter {
		Long taskId;    // 요청 과제 식별자 ID
		Long userId;    // 유저 식별자 ID
		Long requestRecruitmentId;    // 지원 분야 식별자 ID
		Boolean isOpenProfile;    // 자기소개 및 포트폴리오 공개 여부
		String message;    // 추가 메시지
	}

	@Builder
	@Getter
	public static class Response {
		Long requestTaskPurchasingId;        // 요청 과제 구매 식별자 ID
	}
}
