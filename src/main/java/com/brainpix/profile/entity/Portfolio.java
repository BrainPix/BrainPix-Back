package com.brainpix.profile.entity;

import java.time.YearMonth;
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
public class Portfolio extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Specialization> specializationList;

	private YearMonth startDate;
	private YearMonth endDate;

	private String content;

	@ManyToOne
	private Profile profile;

	private String imageUrl;

	@Builder
	public Portfolio(String title, List<Specialization> specializationList, YearMonth startDate, YearMonth endDate,
		String content, Profile profile,String imageUrl) {
		this.title = title;
		this.specializationList = specializationList;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.profile = profile;
		this.imageUrl=imageUrl;
	}

	public void updatePortfolio(String title, List<Specialization> specializationList, YearMonth startDate,
		YearMonth endDate, String content,String imageUrl) {
		this.title = title;
		this.specializationList = specializationList;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.imageUrl=imageUrl;
	}
}
