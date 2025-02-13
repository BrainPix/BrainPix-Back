package com.brainpix.kakaopay.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.KakaoPayErrorCode;
import com.brainpix.api.code.error.PostErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.IdeaMarketPurchasingRepository;
import com.brainpix.kafka.service.AlarmEventService;
import com.brainpix.kakaopay.api_client.KakaoPayApiClient;
import com.brainpix.kakaopay.converter.KakaoPayApproveDtoConverter;
import com.brainpix.kakaopay.converter.KakaoPayReadyDtoConverter;
import com.brainpix.kakaopay.converter.KakaoPaymentDataDtoConverter;
import com.brainpix.kakaopay.dto.KakaoPayApproveDto;
import com.brainpix.kakaopay.dto.KakaoPayReadyDto;
import com.brainpix.kakaopay.dto.KakaoPaymentDataDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketRepository;
import com.brainpix.redis.service.RedisKakaoPayCacheService;
import com.brainpix.user.entity.User;
import com.brainpix.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayService {

	private final UserRepository userRepository;
	private final IdeaMarketRepository ideaMarketRepository;
	private final IdeaMarketPurchasingRepository ideaMarketPurchasingRepository;
	private final AlarmEventService alarmEventService;
	private final KakaoPayApiClient kakaoPayApiClient;
	private final RedisKakaoPayCacheService redisKakaoPayCacheService;

	@Transactional
	public KakaoPayReadyDto.Response kakaoPayReady(KakaoPayReadyDto.Parameter parameter) {

		// 1. 엔티티 검색
		User buyer = userRepository.findById(parameter.getBuyerId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		User seller = userRepository.findById(parameter.getSellerId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		IdeaMarket ideaMarket = ideaMarketRepository.findById(parameter.getIdeaId())
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		// 2. 검증 로직
		if (buyer == seller) {
			throw new BrainPixException(KakaoPayErrorCode.IDEA_OWNER_PURCHASE_INVALID);    // 작성자는 구매할 수 없음
		}
		if (parameter.getQuantity() > ideaMarket.getPrice().getRemainingQuantity()) {
			throw new BrainPixException(KakaoPayErrorCode.QUANTITY_INVALID);    // 구매 수량이 재고를 초과한 경우
		}
		if (ideaMarket.getWriter() != seller) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);    // 작성자와 판매자가 불일치하는 경우
		}

		// 3. 카카오페이 결제 준비 API 호출
		String orderId = getOrderId();    // 주문 아이디 할당(UUID)

		KakaoPayReadyDto.KakaoApiResponse kakaoApiResponse = Optional.ofNullable(kakaoPayApiClient.requestPaymentReady(
			parameter, orderId, buyer, ideaMarket
		)).orElseThrow(() -> new BrainPixException(KakaoPayErrorCode.API_RESPONSE_ERROR));

		log.info("카카오페이 결제 준비 API 성공");

		// 4. 결제 정보 캐싱
		KakaoPaymentDataDto kakaoPaymentData = KakaoPaymentDataDtoConverter.toKakaoPaymentDataDto(
			kakaoApiResponse.getTid(), buyer.getId(),
			parameter.getQuantity());

		redisKakaoPayCacheService.savePaymentData(orderId, kakaoPaymentData);

		// 5. 최종 응답
		return KakaoPayReadyDtoConverter.toResponse(kakaoApiResponse);
	}

	@Transactional
	public KakaoPayApproveDto.Response kakaoPayApprove(KakaoPayApproveDto.Parameter parameter) {

		// 1. 결제 정보 조회
		KakaoPaymentDataDto kakaoPaymentData = redisKakaoPayCacheService.getPaymentData(parameter.getOrderId())
			.orElseThrow(() -> new BrainPixException(KakaoPayErrorCode.PAYMENT_DATA_NOT_FOUND));

		// 2. 엔티티 검색
		User user = userRepository.findById(parameter.getUserId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		IdeaMarket ideaMarket = ideaMarketRepository.findById(parameter.getIdeaId())
			.orElseThrow(() -> new BrainPixException(PostErrorCode.POST_NOT_FOUND));

		// 3. API 호출 전 검증 로직
		if (kakaoPaymentData.getQuantity() > ideaMarket.getPrice().getRemainingQuantity()) {
			throw new BrainPixException(KakaoPayErrorCode.QUANTITY_INVALID);    // 주문 수량이 재고를 초과한 경우
		}
		if (user.getId() != kakaoPaymentData.getBuyerId()) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);    // API 호출자와 실 구매자가 일치하지 않는 경우
		}

		// 4. 최종 승인 API 호출
		KakaoPayApproveDto.KakaoApiResponse kakaoApiResponse = Optional.ofNullable(
				kakaoPayApiClient.requestPaymentApprove(parameter, kakaoPaymentData))
			.orElseThrow(() -> new BrainPixException(KakaoPayErrorCode.API_RESPONSE_ERROR));

		log.info("카카오페이 결제 최종 승인 API 성공");

		// 6. 재고 감소 (변경 감지)
		Price price = ideaMarket.getPrice();
		price.increaseOccupiedQuantity(kakaoPaymentData.getQuantity());    // 차지된 수량을 증가

		// 7. 결제 내역 생성 및 결제 정보 삭제
		IdeaMarketPurchasing ideaMarketPurchasing = KakaoPayApproveDtoConverter.toIdeaMarketPurchasing(
			kakaoApiResponse,
			user,
			ideaMarket, PaymentDuration.ONCE);

		ideaMarketPurchasingRepository.save(ideaMarketPurchasing);
		redisKakaoPayCacheService.deletePaymentData(parameter.getOrderId());

		// 8. 판매자에게 알람 생성
		alarmEventService.publishIdeaSold(ideaMarket.getWriter().getId(), ideaMarket.getWriter().getName());

		// 9. 최종 응답
		return KakaoPayApproveDtoConverter.toResponse(ideaMarketPurchasing);
	}

	private String getOrderId() {
		return UUID.randomUUID().toString();
	}
}
