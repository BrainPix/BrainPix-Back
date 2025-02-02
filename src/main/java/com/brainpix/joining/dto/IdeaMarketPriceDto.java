package com.brainpix.joining.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaMarketPriceDto {

	@NotNull(message = "가격 입력은 필수입니다.")
	@Min(value = 1, message = "가격은 최소 1원 이상이어야 합니다.")
	private Long price;

	@NotNull(message = "수량 설정은 필수입니다.")
	@Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
	private Long totalQuantity;
}
