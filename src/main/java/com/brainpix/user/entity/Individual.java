package com.brainpix.user.entity;

import java.time.LocalDate;

import com.brainpix.profile.entity.Profile;
import com.brainpix.security.authority.BrainpixAuthority;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Individual extends User {

	@Override
	public String getUserType() {
		return "INDIVIDUAL";
	}

	@Builder
	public Individual(String identifier, String password, String name, String nickName, LocalDate birthday,
		String email,
		String profileImage, Profile profile) {
		super(identifier, password, name, nickName, birthday, email, profileImage, profile);
	}

	@Override
	public BrainpixAuthority getAuthority() {
		return BrainpixAuthority.INDIVIDUAL;
	}
}