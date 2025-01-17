package com.brainpix.post.entity.request_task;

import java.util.List;

import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.dto.RequestTaskRecruitmentDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RequestTaskRecruitment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private RequestTask requestTask;

	private String domain;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Price price;

	@Builder
	public RequestTaskRecruitment(RequestTask requestTask, String domain, Price price) {
		this.requestTask = requestTask;
		this.domain = domain;
		this.price = price;
	}

	public void updateRecruitmentFields(RequestTaskRecruitmentDto recruitmentDto) {
		this.domain = recruitmentDto.getDomain();
		this.price.updatePriceFields(recruitmentDto.getPrice(),
				recruitmentDto.getOccupiedQuantity(), recruitmentDto.getTotalQuantity(),
				recruitmentDto.getPaymentDuration());

	}

	// public void updateRecruitmentFields(String domain, Long price, Long occupiedQuantity, Long totalQuantity,
	// 	PaymentDuration paymentDuration) {
	// 	this.domain = domain;
	// 	if (this.price != null) {
	// 		this.price.updatePriceFields(price, occupiedQuantity, totalQuantity, paymentDuration);
	// 	}
	// }
}
