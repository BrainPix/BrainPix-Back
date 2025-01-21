package com.brainpix.joining.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTaskApplicantsResponse {
	private Long recruitmentId;
	private String domain; // 모집 분야 이름
	private List<RequestTaskBuyerResponse> acceptedBuyers; // 수락된 지원자 리스트
	private List<RequestTaskBuyerResponse> pendingBuyers;  // 대기 중 지원자 리스트
}

