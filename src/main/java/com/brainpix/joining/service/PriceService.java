package com.brainpix.joining.service;

import org.springframework.stereotype.Service;

import com.brainpix.joining.converter.CreateIdeaMarketPriceConverter;
import com.brainpix.joining.converter.CreatePriceConverter;
import com.brainpix.joining.dto.IdeaMarketPriceDto;
import com.brainpix.joining.dto.PriceDto;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.PriceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceService {
	private final PriceRepository priceRepository;
	private final CreatePriceConverter createPriceConverter;
	private final CreateIdeaMarketPriceConverter createIdeaMarketPriceConverter;

	@Transactional
	public Price createPrice(PriceDto priceDto) {

		Price price = createPriceConverter.convertToPrice(priceDto);

		return priceRepository.save(price);
	}

	/*
	@Transactional
	public Price createIdeaMarketPrice(IdeaMarketPriceDto priceDto) {

		Price price = createIdeaMarketPriceConverter.convertToIdeaMarketPrice(priceDto);

		return priceRepository.save(price);
	}

	@Transactional
	public void updatePrice(Price price, PriceDto priceDto) {
		price.updatePriceFields(priceDto.getPrice(), priceDto.getTotalQuantity(), priceDto.getPaymentDuration());
		priceRepository.save(price);
	}
	 */
}
