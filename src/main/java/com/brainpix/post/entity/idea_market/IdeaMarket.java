package com.brainpix.post.entity.idea_market;

import java.util.List;

import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.IdeaMarketUpdateDto;
import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class IdeaMarket extends Post {
	@Enumerated(EnumType.STRING)
	private Specialization specialization;

	@Enumerated(EnumType.STRING)
	private IdeaMarketType ideaMarketType;

	@OneToOne(cascade = CascadeType.ALL)
	private Price price;

	@Builder
	public IdeaMarket(User writer, String title, String content, Boolean openMyProfile, Long viewCount,
		List<String> imageList, PostAuth postAuth, List<String> attachmentFileList,
		Specialization specialization, IdeaMarketType ideaMarketType, Price price) {
		super(writer, title, content, openMyProfile, viewCount, postAuth, specialization, imageList,
			attachmentFileList);
		this.ideaMarketType = ideaMarketType;
		this.price = price;
	}

	public void updateIdeaMarketFields(IdeaMarketUpdateDto updateDto) {
		// Post의 필드를 업데이트
		updateBaseFields(updateDto.getTitle(), updateDto.getContent(), updateDto.getCategory(),
			updateDto.getOpenMyProfile(),
			updateDto.getPostAuth(), updateDto.getImageList(), updateDto.getAttachmentFileList());

		// IdeaMarkte 고유 필드 업데이트
		this.specialization = updateDto.getSpecialization();
		this.ideaMarketType = updateDto.getIdeaMarketType();
		this.price.updateIdeaMarketPriceFields(updateDto.getPrice());
	}
}
