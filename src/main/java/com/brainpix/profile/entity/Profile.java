package com.brainpix.profile.entity;

import java.util.List;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.user.entity.User;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public abstract class Profile extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Specialization> specializationList;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	public Profile(User user, List<Specialization> specializationList) {
		this.user = user;
		this.specializationList = specializationList;
	}
}
