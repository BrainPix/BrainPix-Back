package com.brainpix.profile.entity;

import java.util.List;

import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
public abstract class Profile extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Specialization> specializationList;

	public Profile(List<Specialization> specializationList) {
		this.specializationList = specializationList;
	}

	protected void updateSpecializations(List<Specialization> specializationList) {
		this.specializationList = specializationList;
	}
}
