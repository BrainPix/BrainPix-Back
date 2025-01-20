package com.brainpix.joining.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.joining.entity.quantity.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
