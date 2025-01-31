package com.brainpix.joining.dto;

import com.brainpix.joining.entity.quantity.PaymentDuration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

	@NotNull(message = "가격 입력은 필수입니다.")
	private Long price;

	@NotNull(message = "모집 인원 수 입력은 필수입니다.")
	private Long totalQuantity;

	@NotNull
	private PaymentDuration paymentDuration;
}
