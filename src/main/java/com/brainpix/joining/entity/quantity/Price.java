package com.brainpix.joining.entity.quantity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Price extends BaseQuantity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long price;

	@Enumerated(EnumType.STRING)
	private PaymentDuration paymentDuration;

	@Builder
	public Price(Long totalQuantity, Long occupiedQuantity, Long price, PaymentDuration paymentDuration) {
		super(totalQuantity, occupiedQuantity);
		this.price = price;
		this.paymentDuration = paymentDuration;
	}

	/*
	//기존 참여 인원과 비교하는 로직
	public void updatePriceFields(Long price, Long totalQuantity, PaymentDuration paymentDuration) {

		if (totalQuantity < this.getOccupiedQuantity()) {
			throw new BrainPixException(PriceErrorCode.INVALID_QUANTITY_INPUT);
		}
		this.price = price;
		this.paymentDuration = paymentDuration;
		this.updateQuantityFields(totalQuantity);
	}
	 */

}
