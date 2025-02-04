package com.brainpix.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainpix.profile.entity.Contact;
import com.brainpix.profile.entity.IndividualProfile;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	void deleteByIndividualProfile(IndividualProfile profile);
}
