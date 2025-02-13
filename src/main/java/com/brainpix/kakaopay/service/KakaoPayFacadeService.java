package com.brainpix.kakaopay.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.kakaopay.dto.KakaoPayApproveDto;
import com.brainpix.kakaopay.dto.KakaoPayReadyDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayFacadeService {

	private final KakaoPayService kakaoPayService;
	private final RedissonClient redissonClient;

	@Transactional
	public KakaoPayReadyDto.Response kakaoPayReady(KakaoPayReadyDto.Parameter parameter) {
		return kakaoPayService.kakaoPayReady(parameter);
	}

	// 락 획득 → 트랜잭션 시작 → 트랜잭션 해제 → 락 해제 (락 해제가 커밋 이후로 보장되어야 하므로 Facade 패턴 적용)
	public KakaoPayApproveDto.Response kakaoPayApprove(KakaoPayApproveDto.Parameter parameter) {

		String lockKey = "lock:idea:" + parameter.getIdeaId();
		RLock lock = redissonClient.getLock(lockKey);
		boolean isLocked = false;

		try {
			isLocked = lock.tryLock(5, TimeUnit.SECONDS);    // 5초동안 락 획득 대기
			if (isLocked) {
				log.info("결제 락 획득 성공: {}", lockKey);
				return kakaoPayService.kakaoPayApprove(parameter);    // 승인 서비스 코드 실행
			} else {
				log.info("결제 락 획득 실패: {}", lockKey);
				throw new BrainPixException(CommonErrorCode.INTERNAL_SERVER_ERROR);
			}
		} catch (InterruptedException e) {
			throw new BrainPixException(CommonErrorCode.INTERNAL_SERVER_ERROR);
		} finally {
			lock.unlock();    // 락 해제
			log.info("결제 락 해제 성공: {}", lockKey);
		}
	}
}
