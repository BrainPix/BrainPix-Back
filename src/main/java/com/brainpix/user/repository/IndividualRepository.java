package com.brainpix.user.repository;


import com.brainpix.user.entity.Individual;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  IndividualRepository extends JpaRepository<Individual, Long> {
    Optional<Individual> findById(Long id);

    Optional<Individual> findByIdentifier(String identifier);
}

