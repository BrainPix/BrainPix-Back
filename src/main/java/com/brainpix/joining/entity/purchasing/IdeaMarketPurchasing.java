package com.brainpix.joining.entity.purchasing;

import com.brainpix.joining.entity.quantity.PaymentDuration;
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
public class IdeaMarketPurchasing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User buyer;
	private Long price;
	private PaymentDuration paymentDuration;

	@ManyToOne
	private IdeaMarket ideaMarket;

	@Builder
	public IdeaMarketPurchasing(User buyer, Long price, PaymentDuration paymentDuration, IdeaMarket ideaMarket) {
		this.buyer = buyer;
		this.price = price;
		this.paymentDuration = paymentDuration;
		this.ideaMarket = ideaMarket;
	}
}
