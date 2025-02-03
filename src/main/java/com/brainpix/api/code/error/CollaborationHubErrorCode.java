package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CollaborationHubErrorCode implements ErrorCode {

	// 400 Bad Request - 잘못된 요청
	INVALID_TASK_INPUT(HttpStatus.BAD_REQUEST ,"COLLABORATIONHUB400", "요청 과제 입력값이 올바르지 않습니다."),
	INVALID_RECRUITMENT_INPUT(HttpStatus.BAD_REQUEST,"COLLABORATIONHUBK400", "모집 정보가 잘못되었습니다."),
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "COLLABORATIONHUB400" ,"아이디와 도메인이 필요합니다."),
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COLLABORATIONHUB400" ,"도메인과 모집 정보를 정확히 입력해야 합니다."),
	//INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"REQUESTTASK400", "잘못된 파라미터 값입니다."),

	// 401 Unauthorized - 인증 실패
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"COLLABORATIONHUB401", "로그인이 필요합니다."),

	// 403 Forbidden - 권한 없음
	FORBIDDEN_ACCESS(HttpStatus.UNAUTHORIZED,"COLLABORATIONHUB403", "해당 요청에 대한 권한이 없습니다."),

	// 404 Not Found - 리소스를 찾을 수 없음
	WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND,"COLLABORATIONHUB404", "요청한 게시글을 찾을 수 없습니다."),
	RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"COLLABORATIONHUB404", "해당 모집 정보를 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND,"COLLABORATIONHUB404", "사용자를 찾을 수 없습니다."),

	// 500 Internal Server Error - 서버 내부 오류
	TASK_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"COLLABORATIONHUB500", "협업 광장 게시글 생성 중 오류가 발생했습니다."),
	TASK_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"COLLABORATIONHUB500", "협업 광장 게시글 수정 중 오류가 발생했습니다."),
	TASK_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"COLLABORATIONHUB500", "협업 광장 게시글 삭제 중 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
