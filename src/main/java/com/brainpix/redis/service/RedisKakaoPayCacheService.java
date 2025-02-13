package com.brainpix.redis.service;

import java.time.Duration;
import java.util.Optional;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.brainpix.kakaopay.dto.KakaoPaymentDataDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisKakaoPayCacheService {

	private final RedissonClient redissonClient;
	private final String PAYMENT_DATA_PREFIX = "kakao:paymentData:";

	// 결제 정보 캐싱
	public void savePaymentData(String orderId, KakaoPaymentDataDto kakaoPaymentDataDto) {
		RBucket<KakaoPaymentDataDto> bucket = redissonClient.getBucket(PAYMENT_DATA_PREFIX + orderId);
		bucket.set(kakaoPaymentDataDto, Duration.ofMinutes(5)); // 유효 시간 5분
	}

	// 결제 정보 조회
	public Optional<KakaoPaymentDataDto> getPaymentData(String orderId) {
		RBucket<KakaoPaymentDataDto> bucket = redissonClient.getBucket(PAYMENT_DATA_PREFIX + orderId);
		return Optional.ofNullable(bucket.get());
	}

	// 결제 정보 삭제
	public void deletePaymentData(String orderId) {
		RBucket<KakaoPaymentDataDto> bucket = redissonClient.getBucket(PAYMENT_DATA_PREFIX + orderId);
		bucket.delete();
	}
}
