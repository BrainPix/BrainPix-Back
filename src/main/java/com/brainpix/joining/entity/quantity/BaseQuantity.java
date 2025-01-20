package com.brainpix.joining.entity.quantity;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
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

	public void updateQuantityFields(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void incrementOccupiedQuantity() {
		if (this.occupiedQuantity < this.totalQuantity) {
			this.occupiedQuantity++;
		} else {
			throw new BrainPixException(CommonErrorCode.METHOD_NOT_ALLOWED); //FULL_CAPACITY, "모집이 마감되었습니다.");
		}
	}

}
