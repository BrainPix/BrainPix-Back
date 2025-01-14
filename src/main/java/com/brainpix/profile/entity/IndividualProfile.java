package com.brainpix.profile.entity;

import java.util.List;

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
	public IndividualProfile(List<Specialization> specializationList, String selfIntroduction, Boolean contactOpen,
		Boolean careerOpen, Boolean stackOpen) {
		super(specializationList);
		this.selfIntroduction = selfIntroduction;
		this.ContactOpen = contactOpen;
		this.CareerOpen = careerOpen;
		this.stackOpen = stackOpen;
	}

	// 업데이트 로직을 위한 메서드 추가
	public void updateSelfIntroduction(String selfIntroduction) {
		if (selfIntroduction != null) {
			this.selfIntroduction = selfIntroduction;
		}
	}

	public void updateContactOpen(Boolean contactOpen) {
		if (contactOpen != null) {
			this.ContactOpen = contactOpen;
		}
	}

	public void updateCareerOpen(Boolean careerOpen) {
		if (careerOpen != null) {
			this.CareerOpen = careerOpen;
		}
	}

	public void updateStackOpen(Boolean stackOpen) {
		if (stackOpen != null) {
			this.stackOpen = stackOpen;
		}
	}
}
