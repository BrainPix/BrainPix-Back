package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CollaborationHubErrorCode implements ErrorCode {

	// 400 Bad Request - 잘못된 요청
	INVALID_RECRUITMENT_OWNER(HttpStatus.BAD_REQUEST, "COLLABORATIONHUB400", "글 작성자는 요청 과제에 참여할 수 없습니다."),

	// 404 Not Found - 리소스를 찾을 수 없음
	RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLABORATIONHUB404", "해당 모집 정보를 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLABORATIONHUB404", "사용자를 찾을 수 없습니다."),
	COLLABORATION_NOT_FOUND(HttpStatus.NOT_FOUND, "COLLABORATION404", "요청한 협업을 찾을 수 없습니다."),

	// 409 Conflict - 리소스의 중복 및 충돌
	RECRUITMENT_ALREADY_FULL(HttpStatus.CONFLICT, "COLLABORATION409", "해당 모집은 마감되었습니다."),
	RECRUITMENT_ALREADY_APPLY(HttpStatus.CONFLICT, "COLLABORATION409", "이미 지원한 모집입니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}