package com.brainpix.post.dto;

import com.brainpix.joining.dto.PriceDto;
import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.joining.entity.quantity.Price;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTaskRecruitmentDto {
	private String domain;
	private PriceDto priceDto;
}
