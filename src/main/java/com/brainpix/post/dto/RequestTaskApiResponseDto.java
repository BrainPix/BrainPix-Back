package com.brainpix.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTaskApiResponseDto {

	private String message;
	private Long taskId;
}
