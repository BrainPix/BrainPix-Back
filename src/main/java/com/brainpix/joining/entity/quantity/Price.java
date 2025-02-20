package com.brainpix.joining.entity.quantity;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@BatchSize(size = 10)
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
}
