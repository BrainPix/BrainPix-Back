package com.brainpix.post.entity.request_task;

import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	@JoinColumn(name = "request_task_id")
	private RequestTask requestTask;

	private String domain;

	@OneToOne(orphanRemoval = true)
	private Price price;

	@Builder
	public RequestTaskRecruitment(RequestTask requestTask, String domain, Price price) {
		this.requestTask = requestTask;
		this.domain = domain;
		this.price = price;
	}
}
