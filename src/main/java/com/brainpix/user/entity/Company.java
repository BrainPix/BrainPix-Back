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
public class Company extends User {
	private String companyName;
	private String position;

	@Builder
	public Company(String identifier, String password, String name, LocalDateTime birthday, String email,
		String profileImage, Profile profile, String companyName, String position) {
		super(identifier, password, name, birthday, email, profileImage, profile);
		this.companyName = companyName;
		this.position = position;
	}
}
