package com.brainpix.post.dto.mypostdto;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;

public record ApplicationStatusResponse(
	Long applicantId, // 지원자 ID
	String role, // 지원한 역할
	Long approvedCount, // 현재 승인된 인원
	Long totalCount // 해당 역할에서 모집 인원
) {
	public static ApplicationStatusResponse from(RequestTaskPurchasing purchasing) {

		return new ApplicationStatusResponse(
			purchasing.getBuyer().getId(),
			purchasing.getRequestTaskRecruitment().getDomain(),
			purchasing.getRequestTaskRecruitment().getPrice().getOccupiedQuantity(),
			purchasing.getRequestTaskRecruitment().getPrice().getTotalQuantity()
		);
	}
}