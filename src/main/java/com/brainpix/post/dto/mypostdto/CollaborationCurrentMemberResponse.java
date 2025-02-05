package com.brainpix.post.dto.mypostdto;

import java.util.List;

public record CollaborationCurrentMemberResponse(
	String role, // 역할
	int approvedCount, // 현재 승인된 인원 수
	List<String> memberId // 승인된 멤버 ID
) {
	public static CollaborationCurrentMemberResponse from(String role, List<String> memberId) {
		return new CollaborationCurrentMemberResponse(
			role,
			memberId.size(),
			memberId
		);
	}
}