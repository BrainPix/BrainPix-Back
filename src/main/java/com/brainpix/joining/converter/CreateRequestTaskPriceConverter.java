package com.brainpix.joining.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.RequestTaskPriceDto;
import com.brainpix.joining.entity.quantity.Price;

@Component
public class CreateRequestTaskPriceConverter {

	public Price convertToRequestTaskPrice(RequestTaskPriceDto requestTaskPriceDto) {
		return Price.builder()
			.price(requestTaskPriceDto.getPrice())
			.occupiedQuantity(0L)
			.totalQuantity(requestTaskPriceDto.getTotalQuantity())
			.paymentDuration(requestTaskPriceDto.getPaymentDuration())
			.build();
	}
}
