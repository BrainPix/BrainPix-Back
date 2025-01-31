package com.brainpix.profile.entity;

import java.util.ArrayList;
import java.util.List;

import com.brainpix.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CompanyProfile extends Profile {

	private String businessInformation;

	@OneToMany(mappedBy = "companyProfile", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyInformation> companyInformations = new ArrayList<>();

	private Boolean openInformation;

	@Builder
	public CompanyProfile(User user, List<Specialization> specializationList,
		String businessInformation, Boolean openHomepage,
		List<CompanyInformation> companyInformations) {
		super(user, specializationList);
		this.businessInformation = businessInformation;
		this.openInformation = openHomepage;
		this.companyInformations = companyInformations != null ? companyInformations : new ArrayList<>();
	}
}
