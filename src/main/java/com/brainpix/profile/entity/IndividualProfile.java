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

	public void update(String selfIntroduction, Boolean contactOpen, Boolean careerOpen, Boolean stackOpen) {
		this.selfIntroduction = selfIntroduction;
		this.ContactOpen = contactOpen;
		this.CareerOpen = careerOpen;
		this.stackOpen = stackOpen;
	}

	public void updateSpecializations(List<Specialization> newSpecializations) {
		if (newSpecializations.size() > 2) {
			throw new IllegalArgumentException("전문 분야는 최대 2개까지만 선택할 수 있습니다.");
		}
		// 중복 제거
		List<Specialization> distinctSpecializations = newSpecializations.stream()
			.distinct()
			.toList();
		if (distinctSpecializations.size() != newSpecializations.size()) {
			throw new IllegalArgumentException("중복된 전문 분야는 선택할 수 없습니다.");
		}
		// 업데이트
		this.getSpecializationList().clear();
		this.getSpecializationList().addAll(distinctSpecializations);
	}
}
