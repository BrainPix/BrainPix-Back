package com.brainpix.profile.entity;

import java.time.YearMonth;

import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.Entity;
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
public class Career extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String careerContent;

	private YearMonth startDate;
	private YearMonth endDate;

	@ManyToOne
	private IndividualProfile individualProfile;

	@Builder
	public Career(String careerContent, YearMonth startDate, YearMonth endDate, IndividualProfile individualProfile) {
		this.careerContent = careerContent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.individualProfile = individualProfile;
	}
}
