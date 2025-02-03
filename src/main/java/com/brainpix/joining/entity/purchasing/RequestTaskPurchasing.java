package com.brainpix.joining.entity.purchasing;

import com.brainpix.api.code.error.PurchasingErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
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
	
	private Boolean openProfile;
	private String message;

	@ManyToOne
	private RequestTaskRecruitment requestTaskRecruitment;

	@Builder
	public RequestTaskPurchasing(Long id, User buyer, Long price, PaymentDuration paymentDuration, Boolean accepted,
		Boolean openProfile, String message, RequestTaskRecruitment requestTaskRecruitment) {
		this.id = id;
		this.buyer = buyer;
		this.price = price;
		this.paymentDuration = paymentDuration;
		this.accepted = accepted;
		this.openProfile = openProfile;
		this.message = message;
		this.requestTaskRecruitment = requestTaskRecruitment;
	}

	public void validateBuyer(User user) {
		if (!this.buyer.equals(user)) {
			throw new BrainPixException(PurchasingErrorCode.NOT_AUTHORIZED);
		}
	}

	public void validateRejectedStatus() {
		if (Boolean.TRUE.equals(this.accepted)) {
			throw new BrainPixException(PurchasingErrorCode.INVALID_STATUS);
		}
	}
}
