package com.brainpix.post.entity.request_task;

import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.jpa.BaseTimeEntity;

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

	private Integer currentPeople; // 현재 인원

	private Integer totalPeople;   // 모집 인원

	private Integer price;

	@Enumerated(EnumType.STRING)
	private AgreementType agreementType;

	@Builder
	public RequestTaskRecruitment(RequestTask requestTask, String domain, Integer price, Integer currentPeople, Integer totalPeople, AgreementType agreementType) {
		this.requestTask = requestTask;
		this.domain = domain;
		this.price = price;
		this.currentPeople = currentPeople;
		this.totalPeople = totalPeople;
		this.agreementType = agreementType;
	}
}
