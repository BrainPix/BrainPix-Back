package com.brainpix.kakaopay.api_client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.brainpix.kakaopay.dto.KakaoPayApproveDto;
import com.brainpix.kakaopay.dto.KakaoPayReadyDto;
import com.brainpix.kakaopay.dto.KakaoPaymentDataDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.User;

@Component
public class KakaoPayApiClient {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${kakao.pay.ready-url}")
	private String READY_URL;
	@Value("${kakao.pay.approve-url}")
	private String APPROVE_URL;
	@Value("${kakao.pay.redirect-domain}")
	private String REDIRECT_DOMAIN;

	@Value("${kakao.pay.cid}")
	private String cid;        // TC0ONETIME (테스트용 가맹점 cid)
	@Value("${kakao.pay.secret-key}")
	private String secretKey;    // secret-key(dev) 값 (테스트용 시크릿 값)

	// 카카오페이 결제 준비 API
	public KakaoPayReadyDto.KakaoApiResponse requestPaymentReady(
		KakaoPayReadyDto.Parameter parameter, String orderId, User buyer, IdeaMarket ideaMarket) {

		Map<String, String> parameters = getReadyParams(parameter, orderId, buyer, ideaMarket);
		HttpHeaders headers = getHeaders();

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

		ResponseEntity<KakaoPayReadyDto.KakaoApiResponse> response = restTemplate.postForEntity(
			READY_URL, requestEntity, KakaoPayReadyDto.KakaoApiResponse.class
		);

		return response.getBody();
	}

	// 카카오페이 결제 승인 API
	public KakaoPayApproveDto.KakaoApiResponse requestPaymentApprove(
		KakaoPayApproveDto.Parameter parameter, KakaoPaymentDataDto kakaoPaymentData) {

		Map<String, String> parameters = getApproveParams(parameter, kakaoPaymentData);
		HttpHeaders headers = getHeaders();

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

		ResponseEntity<KakaoPayApproveDto.KakaoApiResponse> response = restTemplate.postForEntity(
			APPROVE_URL, requestEntity, KakaoPayApproveDto.KakaoApiResponse.class
		);

		return response.getBody();
	}

	// 결제 준비 파라미터 생성
	private Map<String, String> getReadyParams(
		KakaoPayReadyDto.Parameter parameter, String orderId, User buyer, IdeaMarket ideaMarket) {

		Map<String, String> params = new HashMap<>();

		params.put("cid", cid);
		params.put("partner_order_id", orderId);
		params.put("partner_user_id", String.valueOf(buyer.getId()));
		params.put("item_name", ideaMarket.getTitle());
		params.put("quantity", String.valueOf(parameter.getQuantity()));
		params.put("total_amount", String.valueOf(parameter.getTotalPrice()));
		params.put("tax_free_amount", "0");
		params.put("vat_amount", String.valueOf(parameter.getVat()));
		params.put("approval_url",
			REDIRECT_DOMAIN + "/purchase/approve?ideaId=" + ideaMarket.getId() + "&orderId=" + orderId);
		params.put("cancel_url", REDIRECT_DOMAIN + "/purchase/cancel?ideaId=" + ideaMarket.getId());
		params.put("fail_url", REDIRECT_DOMAIN + "/purchase/fail?ideaId=" + ideaMarket.getId());

		return params;
	}

	// 결제 승인 파라미터 생성
	private Map<String, String> getApproveParams(KakaoPayApproveDto.Parameter parameter,
		KakaoPaymentDataDto kakaoPaymentData) {

		Map<String, String> params = new HashMap<>();

		params.put("cid", cid);
		params.put("tid", kakaoPaymentData.getTid());
		params.put("partner_order_id", parameter.getOrderId());
		params.put("partner_user_id", String.valueOf(kakaoPaymentData.getBuyerId()));
		params.put("pg_token", parameter.getPgToken());

		return params;
	}

	// API 호출에 필요한 헤더 생성
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "SECRET_KEY " + secretKey);
		headers.set("Content-type", "application/json");
		return headers;
	}
}
