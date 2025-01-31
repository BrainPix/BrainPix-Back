package com.brainpix.post.converter;

import org.springframework.stereotype.Component;

import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.IdeaMarketCreateDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.User;

@Component
public class CreateIdeaMarketConverter {

	public IdeaMarket convertToIdeaMarket(IdeaMarketCreateDto createDto, User writer, Price price) {
		return IdeaMarket.builder()
			.writer(writer)
			.title(createDto.getTitle())
			.content(createDto.getContent())
			.specialization(createDto.getSpecialization())
			.openMyProfile(createDto.getOpenMyProfile())
			.viewCount(0L) // 초기 조회수는 0으로 설정
			.imageList(createDto.getImageList())
			.attachmentFileList(createDto.getAttachmentFileList())
			.postAuth(createDto.getPostAuth())
			.ideaMarketType(createDto.getIdeaMarketType())
			.price(price)
			.build();
	}
}
