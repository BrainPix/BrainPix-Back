package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdeaMarketErrorCode implements ErrorCode {

	// 400 Bad Request - 잘못된 요청
	INVALID_IDEA_INPUT(HttpStatus.BAD_REQUEST ,"IDEAMARKET400", "아이디어 마켓 입력값이 올바르지 않습니다."),
	INVALID_RECRUITMENT_INPUT(HttpStatus.BAD_REQUEST,"IDEAMARKET400", "모집 정보가 잘못되었습니다."),
	//INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"REQUESTTASK400", "잘못된 파라미터 값입니다."),

	// 401 Unauthorized - 인증 실패
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"IDEAMARKET401", "로그인이 필요합니다."),

	// 403 Forbidden - 권한 없음
	FORBIDDEN_ACCESS(HttpStatus.UNAUTHORIZED,"IDEAMARKET403", "해당 요청에 대한 권한이 없습니다."),

	// 404 Not Found - 리소스를 찾을 수 없음
	IDEA_NOT_FOUND(HttpStatus.NOT_FOUND,"IDEAMARKET404", "아이디어 마켓 게시글을 찾을 수 없습니다."),
	RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"IDEAMARKET404", "해당 모집 정보를 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND,"IDEAMARKET404", "사용자를 찾을 수 없습니다."),

	// 500 Internal Server Error - 서버 내부 오류
	IDEA_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"IDEAMARKET500", "아이디어 마켓 게시글 생성 중 오류가 발생했습니다."),
	IDEA_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"IDEAMARKET500", "아이디어 마켓 게시글 수정 중 오류가 발생했습니다."),
	IDEA_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"IDEAMARKET500", "아이디어 마켓 게시글 삭제 중 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
