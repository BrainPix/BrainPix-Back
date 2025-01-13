package com.brainpix.api.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.brainpix.api.ApiResponse;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.ErrorCode;
import com.brainpix.api.exception.BrainPixException;

import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionAdvice
 * 모든 예외 처리가 구현되지 않았습니다
 * 테스트하시다가 공통 api 응답 처리가 되지 않으면 추가 부탁드립니다
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BrainPixException.class)
	public ResponseEntity<Object> handleRestApiException(BrainPixException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		return handleExceptionInternal(errorCode);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
		log.warn("handleIllegalArgument");
		ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
		return handleExceptionInternal(errorCode, ex.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.warn("handleHttpRequestMethodNotSupportedException");
		ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
		return handleExceptionInternal(errorCode);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
		HttpStatusCode status, WebRequest request) {

		log.warn("MethodArgumentNotValidException ");
		ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
		return handleExceptionInternal(errorCode, getDefaultMessage(e));
	}

	private static String getDefaultMessage(MethodArgumentNotValidException e) {
		StringBuilder message = new StringBuilder();
		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			message.append(error.getDefaultMessage()).append("\u00a0");
		}
		return message.toString();
	}

	private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ApiResponse.createFail(errorCode));
	}

	private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode, final String message) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ApiResponse.createFail(errorCode, message));
	}
}

