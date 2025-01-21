package com.brainpix.post.entity.request_task;

import java.util.ArrayList;
import java.util.List;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@OneToOne
	private Price price;

	@OneToMany(mappedBy = "requestTaskRecruitment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RequestTaskPurchasing> purchases = new ArrayList<>();

	@Builder
	public RequestTaskRecruitment(RequestTask requestTask, String domain, Price price) {
		this.requestTask = requestTask;
		this.domain = domain;
		this.price = price;
	}
}
