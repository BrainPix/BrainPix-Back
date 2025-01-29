package com.brainpix.profile.entity;

import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Contact extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ContactType type;

	private String value;

	@ManyToOne
	private IndividualProfile individualProfile;

	public Contact(ContactType type, String value, IndividualProfile individualProfile) {
		this.type = type;
		this.value = value;
		this.individualProfile = individualProfile;
	}
}
