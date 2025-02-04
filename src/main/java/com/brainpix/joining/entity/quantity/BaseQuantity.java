package com.brainpix.joining.entity.quantity;

import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class BaseQuantity extends BaseTimeEntity {
	private Long totalQuantity;
	private Long occupiedQuantity;

	@Version
	private Long version;

	public BaseQuantity(Long totalQuantity, Long occupiedQuantity) {
		this.totalQuantity = totalQuantity;
		this.occupiedQuantity = occupiedQuantity;
	}
}
