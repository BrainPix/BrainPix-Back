package com.brainpix.api.code.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestTaskErrorCode implements ErrorCode {

	// 400 Bad Request - 잘못된 요청
	INVALID_TASK_INPUT(HttpStatus.BAD_REQUEST, "REQUESTTASK400", "요청 과제 입력값이 올바르지 않습니다."),
	INVALID_RECRUITMENT_INPUT(HttpStatus.BAD_REQUEST, "REQUESTTASK400", "모집 정보가 잘못되었습니다."),
	INVALID_RECRUITMENT_OWNER(HttpStatus.BAD_REQUEST, "REQUESTTASK400", "글 작성자는 요청 과제에 참여할 수 없습니다."),

	// 401 Unauthorized - 인증 실패
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "REQUESTTASK401", "로그인이 필요합니다."),

	// 403 Forbidden - 권한 없음
	FORBIDDEN_ACCESS(HttpStatus.UNAUTHORIZED, "REQUESTTASK403", "해당 요청에 대한 권한이 없습니다."),

	// 404 Not Found - 리소스를 찾을 수 없음
	TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "REQUESTTASK404", "요청한 과제를 찾을 수 없습니다."),
	RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "REQUESTTASK404", "해당 모집 정보를 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "REQUESTTASK404", "사용자를 찾을 수 없습니다."),

	// 409 Conflict - 리소스의 중복 및 충돌
	RECRUITMENT_ALREADY_FULL(HttpStatus.CONFLICT, "REQUESTTASK409", "해당 모집은 마감되었습니다."),
	RECRUITMENT_ALREADY_APPLY(HttpStatus.CONFLICT, "REQUESTTASK409", "이미 지원한 모집입니다."),

	// 500 Internal Server Error - 서버 내부 오류
	TASK_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REQUESTTASK500", "요청 과제 생성 중 오류가 발생했습니다."),
	TASK_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REQUESTTASK500", "요청 과제 수정 중 오류가 발생했습니다."),
	TASK_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REQUESTTASK500", "요청 과제 삭제 중 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
