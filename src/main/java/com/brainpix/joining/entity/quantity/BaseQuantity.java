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

	public void updateQuantityFields(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	// 모집 인원을 증가시키는 로직
	public void increaseOccupiedQuantity(Long amount) {
		if (this.occupiedQuantity + amount > this.totalQuantity) {
			throw new IllegalStateException("모집 정원을 초과할 수 없습니다.");
		}
		this.occupiedQuantity += amount; // 안전하게 증가
	}
}
