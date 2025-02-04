package com.brainpix.kakaopay.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.IdeaMarketErrorCode;
import com.brainpix.api.code.error.KakaoPayErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.entity.purchasing.IdeaMarketPurchasing;
import com.brainpix.joining.entity.quantity.PaymentDuration;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.joining.repository.IdeaMarketPurchasingRepository;
import com.brainpix.kafka.service.AlarmEventService;
import com.brainpix.kakaopay.converter.KakaoPayApproveDtoConverter;
import com.brainpix.kakaopay.converter.KakaoPayReadyDtoConverter;
import com.brainpix.kakaopay.dto.KakaoPayApproveDto;
import com.brainpix.kakaopay.dto.KakaoPayReadyDto;
import com.brainpix.kakaopay.entity.KakaoPaymentData;
import com.brainpix.kakaopay.repository.KakaoPaymentDataRepository;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.repository.IdeaMarketRepository;
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
	private final KakaoPaymentDataRepository kakaoPaymentDataRepository;
	private final AlarmEventService alarmEventService;

	@Value("${kakao.pay.secret-key}")
	private String secretKey;    // secret-key(dev) 값 (테스트용 시크릿 값)
	@Value("${kakao.pay.cid}")
	private String cid;        // TC0ONETIME (테스트용 가맹점 cid)

	@Transactional
	public KakaoPayReadyDto.Response kakaoPayReady(KakaoPayReadyDto.Parameter parameter) {

		// 1. 엔티티 검색
		User buyer = userRepository.findById(parameter.getBuyerId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		User seller = userRepository.findById(parameter.getSellerId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		IdeaMarket ideaMarket = ideaMarketRepository.findById(parameter.getIdeaId())
			.orElseThrow(() -> new BrainPixException(IdeaMarketErrorCode.IDEA_NOT_FOUND));

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

		// 3. 파라미터 및 헤더 설정
		Map<String, String> parameters = new HashMap<>();
		String orderId = getOrderId();    // 주문 아이디 할당(UUID)

		parameters.put("cid", cid);    // 가맹점 cid
		parameters.put("partner_order_id", orderId);    // 주문 아이디
		parameters.put("partner_user_id", String.valueOf(buyer.getId()));    // 구매자 아이디
		parameters.put("item_name", ideaMarket.getTitle());    // 상품명
		parameters.put("quantity", String.valueOf(parameter.getQuantity()));    // 상품 수량
		parameters.put("total_amount", String.valueOf(parameter.getTotalPrice()));    // 상품 총액
		parameters.put("tax_free_amount", "0");    // 비과세 금액
		parameters.put("vat_amount", String.valueOf(parameter.getVat()));    // vat 금액
		parameters.put("approval_url", "http://localhost:3000/approve?orderId=" + orderId);    // 결제 요청 성공 시 URL
		parameters.put("cancel_url", "http://localhost:3000/cancel");    // 결제 취소 시 URL
		parameters.put("fail_url", "http://localhost:3000/fail");    // 결제 실패 시 URL

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, getRequestHeaders());

		// 4. 카카오페이 결제 준비 API 호출
		RestTemplate restTemplate = new RestTemplate();
		String readyUrl = "https://open-api.kakaopay.com/online/v1/payment/ready";
		ResponseEntity<KakaoPayReadyDto.KakaoApiResponse> result = restTemplate.postForEntity(readyUrl,
			requestEntity, KakaoPayReadyDto.KakaoApiResponse.class);

		KakaoPayReadyDto.KakaoApiResponse kakaoApiResponse = Optional.ofNullable(result.getBody())
			.orElseThrow(() -> new BrainPixException(KakaoPayErrorCode.API_RESPONSE_ERROR));

		log.info("카카오페이 결제 준비 API 성공");

		// 5. 결제 정보 DB에 저장
		KakaoPaymentData kakaoPaymentData = KakaoPayReadyDtoConverter.toKakaoPaymentData(kakaoApiResponse,
			parameter, orderId, buyer, ideaMarket);

		kakaoPaymentDataRepository.save(kakaoPaymentData);

		// 6. 최종 응답
		return KakaoPayReadyDtoConverter.toResponse(kakaoApiResponse, orderId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public KakaoPayApproveDto.Response kakaoPayApprove(KakaoPayApproveDto.Parameter parameter) {

		// 1. DB에서 결제 정보 조회
		KakaoPaymentData kakaoPaymentData = kakaoPaymentDataRepository.findByOrderId(parameter.getOrderId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));

		// 2. 엔티티 검색
		User user = userRepository.findById(parameter.getUserId())
			.orElseThrow(() -> new BrainPixException(CommonErrorCode.RESOURCE_NOT_FOUND));
		IdeaMarket ideaMarket = ideaMarketRepository.findById(kakaoPaymentData.getIdeaMarket().getId())
			.orElseThrow(() -> new BrainPixException(IdeaMarketErrorCode.IDEA_NOT_FOUND));

		// 3. API 호출 전 검증 로직
		if (kakaoPaymentData.getQuantity() > ideaMarket.getPrice().getRemainingQuantity()) {
			throw new BrainPixException(KakaoPayErrorCode.QUANTITY_INVALID);    // 주문 수량이 재고를 초과한 경우
		}
		if (user != kakaoPaymentData.getBuyer()) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);    // API 호출자와 실 구매자가 일치하지 않는 경우
		}

		// 4. 파라미터 및 헤더 설정
		Map<String, String> parameters = new HashMap<>();
		parameters.put("cid", cid);
		parameters.put("tid", kakaoPaymentData.getTid());
		parameters.put("partner_order_id", parameter.getOrderId());
		parameters.put("partner_user_id", String.valueOf(kakaoPaymentData.getBuyer().getId()));
		parameters.put("pg_token", parameter.getPgToken());

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, getRequestHeaders());

		// 5. 최종 승인 API 호출
		RestTemplate restTemplate = new RestTemplate();
		String approveUrl = "https://open-api.kakaopay.com/online/v1/payment/approve";
		ResponseEntity<KakaoPayApproveDto.KakaoApiResponse> result = restTemplate.postForEntity(approveUrl,
			requestEntity, KakaoPayApproveDto.KakaoApiResponse.class);

		KakaoPayApproveDto.KakaoApiResponse kakaoApiResponse = Optional.ofNullable(result.getBody())
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
		kakaoPaymentDataRepository.delete(kakaoPaymentData);

		// 8. 판매자에게 알람 생성
		alarmEventService.publishIdeaSold(ideaMarket.getWriter().getId(), ideaMarket.getWriter().getName());

		// 9. 최종 응답
		return KakaoPayApproveDtoConverter.toResponse(ideaMarketPurchasing);
	}

	private HttpHeaders getRequestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "SECRET_KEY " + secretKey);
		headers.set("Content-type", "application/json");
		return headers;
	}

	private String getOrderId() {
		return UUID.randomUUID().toString();
	}
}
