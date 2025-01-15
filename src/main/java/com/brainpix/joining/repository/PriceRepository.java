package com.brainpix.joining.repository;

import com.brainpix.joining.entity.quantity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    // 필요 시 추가적인 쿼리 메서드 정의
}