package com.brainpix.api.exception;

import com.brainpix.api.code.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BrainPixException extends RuntimeException {

	private final ErrorCode errorCode;
}
