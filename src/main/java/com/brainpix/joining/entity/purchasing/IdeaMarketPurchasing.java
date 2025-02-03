package com.brainpix.joining.entity.purchasing;

import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.entity.idea_market.IdeaMarket;
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
public class IdeaMarketPurchasing extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User buyer;
	private Long price;
	private PaymentDuration paymentDuration;

	private Payment payment;    // 결제 방식
	private Long vat;    // 수수료
	private Long quantity;    // 구매 수량

	@ManyToOne
	private IdeaMarket ideaMarket;

	@Builder
	public IdeaMarketPurchasing(Long id, User buyer, Long price, PaymentDuration paymentDuration, Payment payment,
		Long vat,
		Long quantity, IdeaMarket ideaMarket) {
		this.id = id;
		this.buyer = buyer;
		this.price = price;
		this.paymentDuration = paymentDuration;
		this.payment = payment;
		this.vat = vat;
		this.quantity = quantity;
		this.ideaMarket = ideaMarket;
	}
}
