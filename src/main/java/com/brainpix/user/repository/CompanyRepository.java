package com.brainpix.user.repository;

import com.brainpix.user.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findById(Long id);

    Optional<Company> findByIdentifier(String identifier);
}
