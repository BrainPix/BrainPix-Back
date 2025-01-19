package com.brainpix.joining.entity.quantity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Gathering extends BaseQuantity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder
	public Gathering(Long totalQuantity, Long occupiedQuantity) {
		super(totalQuantity, occupiedQuantity);
	}

	public void updateGatheringFields(Long occupiedQuantity, Long totalQuantity) {
		this.updateQuantityFields(occupiedQuantity, totalQuantity);
	}
}
