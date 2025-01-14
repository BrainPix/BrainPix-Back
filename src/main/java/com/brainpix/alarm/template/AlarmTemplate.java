package com.brainpix.alarm.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmTemplate {
	// Q&A 관련
	QNA_COMMENT("${postType} > 담당자 Q&A", "${postWriter}님의 게시글에 ${commentWriter}님이 댓글을 남겼습니다.", "url 주소"),
	QNA_COMMENT_REPLY("${postType} > 담당자 Q&A", "${postWriter}님의 게시글에 ${commentWriter}님이 댓글을 남겼습니다.", "url 주소"),

	// 아이디어 마켓 관련
	IDEA_SOLD("아이디어 마켓", "${postWriter}님의 아이디어가 판매되었습니다.", "url 주소"),

	// 요청 과제 관련
	REQUEST_TASK_APPLY("요청 과제", "${postWriter}님의 과제에 ${applicant}님이 지원하셨습니다.", "url 주소"),
	REQUEST_TASK_APPROVE("요청 과제", "${applicant}님이 지원하신 과제가 승낙 되었습니다", "url 주소"),

	// 협업 광장 관련
	COLLABORATION_TASK_APPLY("협업 광장", "${applicant}님이 ${postWriter}님의 팀에 지원하셨습니다", "url 주소"),
	COLLABORATION_TASK_APPROVE("협업 광장", "${applicant}님이 팀${postWriter}에 승인 되었습니다", "url 주소"),
	COLLABORATION_TASK_REJECT("협업 광장", "${applicant}님이 팀${postWriter}에 거절 되었습니다", "url 주소");
	private final String messageTemplate;
	private final String headerTemplate;
	private final String redirectUrl;
}
