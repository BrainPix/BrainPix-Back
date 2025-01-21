package com.brainpix.joining.entity.purchasing;

import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RequestTaskPurchasing extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User buyer;
	private Long price;
	private PaymentDuration paymentDuration;

	private Boolean accepted;

	@ManyToOne(fetch = FetchType.LAZY)
	private RequestTaskRecruitment requestTaskRecruitment;

	@Builder
	public RequestTaskPurchasing(User buyer, Long price, PaymentDuration paymentDuration, Boolean accepted,
		RequestTaskRecruitment requestTaskRecruitment) {
		this.buyer = buyer;
		this.price = price;
		this.paymentDuration = paymentDuration;
		this.accepted = accepted;
		this.requestTaskRecruitment = requestTaskRecruitment;
	}
}
