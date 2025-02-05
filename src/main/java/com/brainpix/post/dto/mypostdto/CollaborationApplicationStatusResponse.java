package com.brainpix.post.dto.mypostdto;

import com.brainpix.joining.entity.purchasing.CollectionGathering;

public record CollaborationApplicationStatusResponse(
	String applicantId, // 지원자 ID
	String role, // 지원한 역할
	Long approvedCount, // 현재 승인된 인원
	Long totalCount // 해당 역할에서 모집 인원
) {
	public static CollaborationApplicationStatusResponse from(CollectionGathering collectionGathering) {

		return new CollaborationApplicationStatusResponse(
			collectionGathering.getJoiner().getIdentifier(),
			collectionGathering.getCollaborationRecruitment().getDomain(),
			collectionGathering.getCollaborationRecruitment().getGathering().getOccupiedQuantity(),
			collectionGathering.getCollaborationRecruitment().getGathering().getTotalQuantity()
		);
	}
}