package com.brainpix.joining.dto;

import com.brainpix.joining.entity.quantity.PaymentDuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaMarketPriceDto {

	private Long price;
	private Long totalQuantity;
}
