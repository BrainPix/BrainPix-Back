package com.brainpix.joining.service;

import org.springframework.stereotype.Service;

import com.brainpix.joining.dto.PriceDto;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.PriceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceService {
	private final PriceRepository priceRepository;

	@Transactional
	public Price createPrice(PriceDto priceDto) {
		/*if (priceDto.getPrice() == null || priceDto.getTotalQuantity() == null || priceDto.getPaymentDuration() == null) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER, "가격 정보가 올바르지 않습니다.");
		}*/

		Price price = Price.builder()
			.price(priceDto.getPrice())
			.occupiedQuantity(0L)
			.totalQuantity(priceDto.getTotalQuantity())
			.paymentDuration(priceDto.getPaymentDuration())
			.build();

		return priceRepository.save(price);
	}

	@Transactional
	public void updatePrice(Price price, PriceDto priceDto) {
		price.updatePriceFields(priceDto.getPrice(), priceDto.getTotalQuantity(), priceDto.getPaymentDuration());
		priceRepository.save(price);
	}
}
