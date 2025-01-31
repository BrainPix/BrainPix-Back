package com.brainpix.joining.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDto {

	@NotNull(message = "모집 인원 수 입력은 필수입니다.")
	private Long totalQuantity;
}
