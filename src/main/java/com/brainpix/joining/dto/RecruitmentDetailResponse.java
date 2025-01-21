package com.brainpix.joining.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecruitmentDetailResponse {
	private Long recruitmentId;
	private String domain;
	private Long price;
	private Long currentQuantity;
	private Long totalQuantity;
}