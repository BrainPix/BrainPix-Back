package com.brainpix.user.entity;

import jakarta.persistence.Table;
import java.time.LocalDateTime;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.profile.entity.Profile;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users") //user 테이블 이름과 SQL 예약어 충돌 (테스트할때 충돌나서 넣었습니다)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
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

	public long getProfileId() {
		return profile.getId();
	}
}
