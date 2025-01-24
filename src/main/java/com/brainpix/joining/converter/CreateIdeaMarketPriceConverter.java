package com.brainpix.joining.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.dto.IdeaMarketPriceDto;
import com.brainpix.joining.dto.PriceDto;
import com.brainpix.joining.entity.quantity.Price;

@Component
public class CreateIdeaMarketPriceConverter {

	public Price convertToIdeaMarketPrice(IdeaMarketPriceDto priceDto) {
		return Price.builder()
			.price(priceDto.getPrice())
			.occupiedQuantity(null)
			.totalQuantity(priceDto.getTotalQuantity())
			.paymentDuration(null)
			.build();
	}
}
