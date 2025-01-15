package com.brainpix.user.entity;

import java.time.LocalDateTime;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.profile.entity.Profile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
// @Table(name = "`user`")
public abstract class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String identifier;
	private String password;
	private String name;
	private LocalDateTime birthday;
	private String email;
	private String profileImage;

	@OneToOne(fetch = FetchType.LAZY)
	private Profile profile;

	public User(String identifier, String password, String name, LocalDateTime birthday, String email,
		String profileImage, Profile profile) {
		this.identifier = identifier;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.email = email;
		this.profileImage = profileImage;
		this.profile = profile;
	}
}
