package com.brainpix.post.dto.mypostdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestTaskCurrentMember {
	private String userId;        // 수락된 사용자 아이디
	private String role;          // 역할
	private Long memberCount;     // 해당 역할의 수락된 인원 수

}