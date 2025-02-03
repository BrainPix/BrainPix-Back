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
public class Company extends User {
	private String position;

	@Builder
	public Company(String identifier, String password, String name, String nickName, LocalDate birthday,
		String email, String profileImage, Profile profile, String position) {
		super(identifier, password, name, nickName, birthday, email, profileImage, profile);
		this.position = position;
	}

	@Override
	public BrainpixAuthority getAuthority() {
		return BrainpixAuthority.COMPANY;
	}
}
