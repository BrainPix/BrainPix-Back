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
public class IndividualProfile extends Profile {
	private String selfIntroduction;
	private Boolean ContactOpen;
	private Boolean CareerOpen;
	private Boolean stackOpen;

	@Builder
	public IndividualProfile(User user, List<Specialization> specializationList, String selfIntroduction,
		Boolean contactOpen, Boolean careerOpen, Boolean stackOpen) {
		super(user, specializationList);
		this.selfIntroduction = selfIntroduction;
		this.ContactOpen = contactOpen;
		this.CareerOpen = careerOpen;
		this.stackOpen = stackOpen;
	}
}
