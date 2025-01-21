package com.brainpix.joining.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.PriceDto;
import com.brainpix.joining.entity.quantity.Price;

@Component
public class CreatePriceConverter {

	public Price convertToPrice(PriceDto priceDto) {
		return Price.builder()
			.price(priceDto.getPrice())
			.occupiedQuantity(0L)
			.totalQuantity(priceDto.getTotalQuantity())
			.paymentDuration(priceDto.getPaymentDuration())
			.build();
	}
}
