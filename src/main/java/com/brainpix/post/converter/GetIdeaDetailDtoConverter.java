package com.brainpix.post.converter;

import java.util.List;

import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.User;

public class GetIdeaDetailDtoConverter {

	public static GetIdeaDetailDto.Parameter toParameter(Long ideaId) {
		return GetIdeaDetailDto.Parameter.builder()
			.ideaId(ideaId)
			.build();
	}

	public static GetIdeaDetailDto.Response toResponse(IdeaMarket ideaMarket, User writer, Long saveCount, Long totalIdeas, Long totalCollaborations) {

		// 작성자
		GetIdeaDetailDto.Writer writerDto = toWriter(writer, totalIdeas, totalCollaborations);

		// 첨부 파일 URL
		List<String> attachments = ideaMarket.getImageList();

		return GetIdeaDetailDto.Response.builder()
			.ideaId(ideaMarket.getId())
			.thumbnailImageUrl(ideaMarket.getImageList().get(0))
			.category(ideaMarket.getSpecialization())
			.ideaMarketType(ideaMarket.getIdeaMarketType())
			.auth(ideaMarket.getPostAuth())
			.title(ideaMarket.getTitle())
			.content(ideaMarket.getContent())
			.price(ideaMarket.getPrice().getPrice())
			.viewCount(ideaMarket.getViewCount())
			.saveCount(saveCount)
			.totalQuantity(ideaMarket.getPrice().getTotalQuantity())
			.occupiedQuantity(ideaMarket.getPrice().getOccupiedQuantity())
			.createdDate(ideaMarket.getCreatedAt().toLocalDate())
			.writer(writerDto)
			.attachments(attachments)
			.build();
	}

	public static GetIdeaDetailDto.Writer toWriter(User writer, Long totalIdeas, Long totalCollaborations) {
		return GetIdeaDetailDto.Writer.builder()
			.writerId(writer.getId())
			.name(writer.getName())
			.profileImageUrl(writer.getProfileImage())
			.role(writer instanceof Company ? "COMPANY" : "INDIVIDUAL")
			.specialization(writer.getProfile().getSpecializationList().get(0))
			.totalIdeas(totalIdeas)
			.totalCollaborations(totalCollaborations)
			.build();
	}
}
