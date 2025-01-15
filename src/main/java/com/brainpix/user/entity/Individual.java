package com.brainpix.user.entity;

import java.time.LocalDateTime;

import com.brainpix.profile.entity.Profile;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Individual extends User {

	@Builder
	public Individual(String identifier, String password, String name, LocalDateTime birthday, String email,
		String profileImage, Profile profile) {
		super(identifier, password, name, birthday, email, profileImage, profile);
	}
}