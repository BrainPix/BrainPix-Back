package com.brainpix.profile.entity;

import java.time.YearMonth;

import com.brainpix.api.code.error.PortfolioErrorCode;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.user.entity.User;

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

	@Enumerated(EnumType.STRING)
	private Specialization specialization;

	private YearMonth startDate;
	private YearMonth endDate;

	private String content;

	@ManyToOne
	private Profile profile;

	private String profileImage;

	@Builder
	public Portfolio(String title, Specialization specialization, YearMonth startDate, YearMonth endDate,
		String content, Profile profile, String profileImage) {
		this.title = title;
		this.specialization = specialization;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.profile = profile;
		this.profileImage = profileImage;
	}

	// 팩토리 메서드
	public static Portfolio create(Profile profile, String title, Specialization specialization,
		YearMonth startDate, YearMonth endDate, String content, String profileImage) {
		return Portfolio.builder()
			.title(title)
			.specialization(specialization)
			.startDate(startDate)
			.endDate(endDate)
			.content(content)
			.profile(profile)
			.profileImage(profileImage)
			.build();
	}

	// 상태 업데이트 메서드
	public void update(String title, Specialization specialization, YearMonth startDate,
		YearMonth endDate, String content, String profileImage) {
		this.title = title;
		this.specialization = specialization;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.profileImage = profileImage;
	}

	public void validateOwnership(User user) {
		if (!this.profile.getUser().equals(user)) {
			throw new IllegalArgumentException(
				PortfolioErrorCode.NOT_OWNED_PORTFOLIO.getMessage()
			);
		}
	}
}
