package com.brainpix.kakaopay.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.kakaopay.dto.KakaoPayApproveDto;
import com.brainpix.kakaopay.dto.KakaoPayReadyDto;
import com.brainpix.kakaopay.repository.NamedLockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayFacadeService {

	private final NamedLockRepository namedLockRepository;
	private final KakaoPayService kakaoPayService;

	@Transactional
	public KakaoPayReadyDto.Response kakaoPayReady(KakaoPayReadyDto.Parameter parameter) {
		return kakaoPayService.kakaoPayReady(parameter);
	}

	@Transactional
	public KakaoPayApproveDto.Response kakaoPayApprove(KakaoPayApproveDto.Parameter parameter) {
		String lockName = "LOCK_KAKAO_PAY_APPROVE_" + parameter.getIdeaId();
		try {
			acquireLock(lockName);
			return kakaoPayService.kakaoPayApprove(parameter);
		} finally {
			releaseLock(lockName);
		}
	}

	private void acquireLock(String lockName) {
		log.info("락 요청 lockName : {}", lockName);
		Integer result = namedLockRepository.getLock(lockName);
		if (result == null || result == 0) {
			throw new BrainPixException(CommonErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	private void releaseLock(String lockName) {
		log.info("락 반환 lockName : {}", lockName);
		namedLockRepository.releaseLock(lockName);
	}
}
