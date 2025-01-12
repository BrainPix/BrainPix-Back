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
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Contact extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<ContactType> contactTypeList;

	@ManyToOne
	private IndividualProfile individualProfile;

	@Builder
	public Contact(List<ContactType> contactTypeList, IndividualProfile individualProfile) {
		this.contactTypeList = contactTypeList;
		this.individualProfile = individualProfile;
	}
}
