package com.brainpix.post.dto;

import com.brainpix.post.entity.request_task.AgreementType;

import lombok.Data;

@Data
public class RequestTaskRecruitmentDto {
	private String domain;
	private Integer currentPeople;
	private Integer totalPeople;
	private Integer price;
	private AgreementType agreementType;
}
