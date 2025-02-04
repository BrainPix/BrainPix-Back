package com.brainpix.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.profile.entity.CompanyInformation;
import com.brainpix.profile.entity.CompanyProfile;

public interface CompanyInformationRepository extends JpaRepository<CompanyInformation, Long> {
	void deleteByCompanyProfile(CompanyProfile companyProfile);
}