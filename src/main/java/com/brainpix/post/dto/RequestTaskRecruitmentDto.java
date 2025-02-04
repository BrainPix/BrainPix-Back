package com.brainpix.post.dto;

import com.brainpix.joining.dto.RequestTaskPriceDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTaskRecruitmentDto {

	@NotBlank(message = "역할은 필수 입력 값입니다.")
	private String domain;

	@Valid
	@NotNull(message = "가격 및 인원, 지급 방식 설정은 필수입니다.")
	private RequestTaskPriceDto requestTaskPriceDto;
}
