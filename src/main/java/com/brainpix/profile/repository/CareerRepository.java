package com.brainpix.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.profile.entity.Career;
import com.brainpix.profile.entity.IndividualProfile;

public interface CareerRepository extends JpaRepository<Career, Long> {
	void deleteByIndividualProfile(IndividualProfile profile);

}
