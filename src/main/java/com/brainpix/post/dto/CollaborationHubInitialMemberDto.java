package com.brainpix.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationHubInitialMemberDto {

	@NotBlank(message = "역할은 필수 입력 값입니다.")
	private String domain;

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String identifier;
}
