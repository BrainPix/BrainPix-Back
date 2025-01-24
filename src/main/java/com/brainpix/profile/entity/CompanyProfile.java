package com.brainpix.profile.entity;

import java.util.List;

import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CompanyProfile extends Profile {
	private String businessType;
	private String businessInformation;
	private String homepage;
	private Boolean openHomepage;

	@Builder
	public CompanyProfile(User user, List<Specialization> specializationList, String businessType,
		String businessInformation,
		String homepage, Boolean openHomepage) {
		super(user, specializationList);
		this.businessType = businessType;
		this.businessInformation = businessInformation;
		this.homepage = homepage;
		this.openHomepage = openHomepage;
	}
}
