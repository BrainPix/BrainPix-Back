package com.brainpix.sale.entity.quantity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public abstract class BaseQuantity {
	private Long totalQuantity;
	private Long occupiedQuantity;

	@Version
	private Long version;
}
