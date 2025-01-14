package com.brainpix.profile.repository;

import com.brainpix.profile.entity.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByIndividualProfileId(Long userId);
    @Transactional
    void deleteByIndividualProfileId(Long individualProfileId);
}