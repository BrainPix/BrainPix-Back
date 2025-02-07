package com.brainpix.post.dto.mypostdto;

import java.util.List;

public record RequestTaskCurrentMemberResponse(
	String role, // 역할
	int approvedCount, // 현재 승인된 인원 수
	List<AcceptedInfo> memberId // 승인된 멤버 ID
) {
	public static RequestTaskCurrentMemberResponse from(String role, List<AcceptedInfo> members) {
		return new RequestTaskCurrentMemberResponse(
			role,
			members.size(),
			members
		);
	}

	public record AcceptedInfo(
		String id,
		String userType,
		Long acceptedMemberId
	) {
		public static AcceptedInfo from(String id, String userType, Long acceptedMemberId) {
			return new AcceptedInfo(id, userType, acceptedMemberId);
		}
	}
}