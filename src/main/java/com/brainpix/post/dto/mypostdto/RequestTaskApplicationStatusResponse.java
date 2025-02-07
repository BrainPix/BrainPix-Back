package com.brainpix.post.dto.mypostdto;

import com.brainpix.joining.entity.purchasing.RequestTaskPurchasing;

public record RequestTaskApplicationStatusResponse(
	String applicantId, // 지원자 ID
	String role, // 지원한 역할
	Long approvedCount, // 현재 승인된 인원
	Long totalCount, // 해당 역할에서 모집 인원
	Long purchasingId
) {
	public static RequestTaskApplicationStatusResponse from(RequestTaskPurchasing purchasing) {

		return new RequestTaskApplicationStatusResponse(
			purchasing.getBuyer().getIdentifier(),
			purchasing.getRequestTaskRecruitment().getDomain(),
			purchasing.getRequestTaskRecruitment().getPrice().getOccupiedQuantity(),
			purchasing.getRequestTaskRecruitment().getPrice().getTotalQuantity(),
			purchasing.getId()
		);
	}
}