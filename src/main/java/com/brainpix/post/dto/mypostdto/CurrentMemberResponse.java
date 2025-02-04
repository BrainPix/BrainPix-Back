package com.brainpix.post.dto.mypostdto;

import java.util.List;

public record CurrentMemberResponse(
	String role, // 역할
	int approvedCount, // 현재 승인된 인원 수
	List<String> memberId // 승인된 멤버 ID
) {
	public static CurrentMemberResponse from(String role, List<String> memberId) {
		return new CurrentMemberResponse(
			role,
			memberId.size(),
			memberId
		);
	}
}