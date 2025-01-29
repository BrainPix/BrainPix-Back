package com.brainpix.profile.entity;

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
public class CompanyInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private CompanyInformationType companyInformationType;

	private String value;

	@ManyToOne
	private CompanyProfile companyProfile;

	@Builder
	public CompanyInformation(CompanyInformationType companyInformationType, String value,
		CompanyProfile companyProfile) {
		this.companyInformationType = companyInformationType;
		this.value = value;
		this.companyProfile = companyProfile;
	}
}