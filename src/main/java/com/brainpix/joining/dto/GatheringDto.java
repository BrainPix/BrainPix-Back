package com.brainpix.joining.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDto {

	@NotNull(message = "모집 인원 수 입력은 필수입니다.")
	@Min(value = 1, message = "인원 수는 최소 1명 이상이어야 합니다.")
	private Long totalQuantity;
}
