package com.brainpix.joining.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brainpix.joining.entity.quantity.Gathering;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {
}
