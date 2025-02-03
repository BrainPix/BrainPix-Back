package com.brainpix.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.entity.Stack;

public interface StackRepository extends JpaRepository<Stack, Long> {
	void deleteByIndividualProfile(IndividualProfile profile);

}
