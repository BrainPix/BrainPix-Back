package com.brainpix.profile.repository;

import com.brainpix.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
