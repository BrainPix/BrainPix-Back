package com.brainpix.kakaopay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.kakaopay.entity.KakaoPaymentData;

public interface KakaoPaymentDataRepository extends JpaRepository<KakaoPaymentData, Long> {

	Optional<KakaoPaymentData> findByOrderId(String orderId);
}
