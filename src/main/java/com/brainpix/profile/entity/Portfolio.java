package com.brainpix.profile.entity;

import java.time.YearMonth;
import java.util.List;

import com.brainpix.api.code.error.PortfolioErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.user.entity.User;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<Specialization> specializationList;

	private YearMonth startDate;
	private YearMonth endDate;

	private String content;

	@ManyToOne
	private Profile profile;

	private String profileImage;

	@Builder
	public Portfolio(String title, List<Specialization> specializationList, YearMonth startDate, YearMonth endDate,
		String content, Profile profile, String profileImage) {
		this.title = title;
		this.specializationList = specializationList;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.profile = profile;
		this.profileImage = profileImage;
	}

	// 팩토리 메서드
	public static Portfolio create(Profile profile, String title, List<Specialization> specializationList,
		YearMonth startDate, YearMonth endDate, String content, String profileImage) {
		return Portfolio.builder()
			.title(title)
			.specializationList(specializationList)
			.startDate(startDate)
			.endDate(endDate)
			.content(content)
			.profile(profile)
			.profileImage(profileImage)
			.build();
	}

	// 상태 업데이트 메서드
	public void update(String title, List<Specialization> specializationList, YearMonth startDate,
		YearMonth endDate, String content, String profileImage) {
		this.title = title;
		this.specializationList = specializationList;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.profileImage = profileImage;
	}

	public void validateOwnership(User user) {
		if (!this.profile.getUser().equals(user)) {
			throw new BrainPixException(PortfolioErrorCode.NOT_OWNED_PORTFOLIO);
		}
	}
}
