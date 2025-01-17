package com.brainpix.post.dto;

import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.joining.entity.quantity.Price;

import lombok.Data;

@Data
public class RequestTaskRecruitmentDto {
	private String domain;
	private Long price;
	private Long totalQuantity;
	private Long occupiedQuantity;
	private PaymentDuration paymentDuration;
}
