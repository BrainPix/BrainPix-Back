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

	// OccupiedQuantity 수정 메서드 추가
	public void incrementOccupiedQuantity() {
		if (this.getOccupiedQuantity() >= this.getTotalQuantity()) {
			throw new IllegalStateException("No more spots available");
		}
		super.setOccupiedQuantity(this.getOccupiedQuantity() + 1);
	}

	public void decrementOccupiedQuantity() {
		if (this.getOccupiedQuantity() <= 0) {
			throw new IllegalStateException("Occupied quantity cannot be negative");
		}
		super.setOccupiedQuantity(this.getOccupiedQuantity() - 1);
	}
}
