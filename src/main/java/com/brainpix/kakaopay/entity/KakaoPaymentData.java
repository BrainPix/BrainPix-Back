package com.brainpix.kakaopay.entity;

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
@Getter
@NoArgsConstructor
public class KakaoPaymentData extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderId;
	private String tid;
	private Long quantity;

	@ManyToOne
	private User buyer;
	@ManyToOne
	private IdeaMarket ideaMarket;

	@Builder
	public KakaoPaymentData(Long id, String orderId, String tid, Long quantity, User buyer, IdeaMarket ideaMarket) {
		this.id = id;
		this.orderId = orderId;
		this.tid = tid;
		this.quantity = quantity;
		this.buyer = buyer;
		this.ideaMarket = ideaMarket;
	}
}
