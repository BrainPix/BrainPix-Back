package com.brainpix.joining.service;

import org.springframework.stereotype.Service;

import com.brainpix.joining.converter.CreateIdeaMarketPriceConverter;
import com.brainpix.joining.converter.CreateRequestTaskPriceConverter;
import com.brainpix.joining.dto.IdeaMarketPriceDto;
import com.brainpix.joining.dto.RequestTaskPriceDto;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.PriceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceService {
	private final PriceRepository priceRepository;
	private final CreateRequestTaskPriceConverter createRequestTaskPriceConverter;
	private final CreateIdeaMarketPriceConverter createIdeaMarketPriceConverter;

	@Transactional
	public Price createRequestTaskPrice(RequestTaskPriceDto requestTaskPriceDto) {

		Price price = createRequestTaskPriceConverter.convertToRequestTaskPrice(requestTaskPriceDto);

		return priceRepository.save(price);
	}

	@Transactional
	public Price createIdeaMarketPrice(IdeaMarketPriceDto ideaMarketPriceDto) {

		Price price = createIdeaMarketPriceConverter.convertToIdeaMarketPrice(ideaMarketPriceDto);

		return priceRepository.save(price);
	}
}
