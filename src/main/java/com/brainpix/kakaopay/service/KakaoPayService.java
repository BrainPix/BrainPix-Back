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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.code.error.IdeaMarketErrorCode;
import com.brainpix.api.code.error.KakaoPayErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.joining.repository.IdeaMarketPurchasingRepository;
import com.brainpix.kafka.service.AlarmEventService;
import com.brainpix.kakaopay.converter.KakaoPayReadyDtoConverter;
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
