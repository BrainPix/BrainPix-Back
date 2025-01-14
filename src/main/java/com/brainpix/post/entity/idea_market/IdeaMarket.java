package com.brainpix.post.entity.idea_market;

import java.util.List;

import com.brainpix.post.entity.BasePost;
import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class IdeaMarket extends BasePost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Specialization specialization;

	@Enumerated(EnumType.STRING)
	private IdeaMarketType ideaMarketType;

	@Enumerated(EnumType.STRING)
	private IdeaMarketAuth ideaMarketAuth;

	@OneToOne
	private Price price;

	@Builder
	public IdeaMarket(User writer, String title, String content, String category, Boolean openMyProfile, Long viewCount,
		List<String> imageList, List<String> attachmentFileList, Specialization specialization,
		IdeaMarketType ideaMarketType, IdeaMarketAuth ideaMarketAuth,
		Price price) {
		super(writer, title, content, category, openMyProfile, viewCount, imageList, attachmentFileList);
		this.specialization = specialization;
		this.ideaMarketType = ideaMarketType;
		this.ideaMarketAuth = ideaMarketAuth;
		this.price = price;
	}
}
