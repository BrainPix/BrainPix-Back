package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

	// 403 Forbidden - 권한 없음
	FORBIDDEN_ACCESS(HttpStatus.UNAUTHORIZED, "POST403", "해당 요청에 대한 권한이 없습니다."),
	IDEA_MARKET_NOT_FOUND(HttpStatus.NOT_FOUND, "IM404", "아이디어 마켓 게시글이 존재하지 않습니다."),
	REQUEST_TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "IM404", "요청 과제 게시글이 존재하지 않습니다."),
	COLLABORATION_HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "IM404", "협업 광장 게시글이 존재하지 않습니다."),
	NOT_POST_OWNER(HttpStatus.FORBIDDEN, "POST403", "본인이 작성한 게시글이 아닙니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
