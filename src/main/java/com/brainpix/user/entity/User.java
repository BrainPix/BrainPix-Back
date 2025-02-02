package com.brainpix.user.entity;

import java.time.LocalDate;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.profile.entity.Profile;
import com.brainpix.security.authority.BrainpixAuthority;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
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
@Table(name = "users") //user 테이블 이름과 SQL 예약어 충돌 (테스트할때 충돌나서 넣었습니다)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
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
	private String nickName;
	private LocalDate birthday;
	private String email;
	private String profileImage;

	@OneToOne(mappedBy = "user")
	private Profile profile;

	public User(String identifier, String password, String name, String nickName, LocalDate birthday, String email,
		String profileImage, Profile profile) {
		this.identifier = identifier;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.birthday = birthday;
		this.email = email;
		this.profileImage = profileImage;
		this.profile = profile;
	}

	public long getProfileId() {
		return profile.getId();
	}

	public abstract BrainpixAuthority getAuthority();
}
