package com.brainpix.joining.repository;


import com.brainpix.joining.entity.quantity.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    // 추가 쿼리가 필요하면 정의
}