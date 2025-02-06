package com.brainpix.post.converter;

import java.util.List;

import com.brainpix.post.dto.GetIdeaDetailDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.User;

public class GetIdeaDetailDtoConverter {

	public static GetIdeaDetailDto.Parameter toParameter(Long ideaId, Long userId) {
		return GetIdeaDetailDto.Parameter.builder()
			.ideaId(ideaId)
			.userId(userId)
			.build();
	}

	public static GetIdeaDetailDto.Response toResponse(IdeaMarket ideaMarket, User writer, Long saveCount,
		Long totalIdeas, Long totalCollaborations) {

		// 작성자
		GetIdeaDetailDto.Writer writerDto = toWriter(writer, totalIdeas, totalCollaborations);

		// 첨부 파일 URL
		List<String> attachments = ideaMarket.getImageList();

		return GetIdeaDetailDto.Response.builder()
			.ideaId(ideaMarket.getId())
			.thumbnailImageUrl(ideaMarket.getImageList() != null ? ideaMarket.getImageList().get(0) : null)
			.category(ideaMarket.getSpecialization().toString())
			.ideaMarketType(ideaMarket.getIdeaMarketType().toString())
			.auth(ideaMarket.getPostAuth().toString())
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
			.openMyProfile(ideaMarket.getOpenMyProfile())
			.build();
	}

	public static GetIdeaDetailDto.Writer toWriter(User writer, Long totalIdeas, Long totalCollaborations) {
		return GetIdeaDetailDto.Writer.builder()
			.writerId(writer.getId())
			.name(writer.getName())
			.profileImageUrl(writer.getProfileImage())
			.role(writer instanceof Company ? "COMPANY" : "INDIVIDUAL")
			.specialization(writer.getProfile().getSpecializationList() != null ?
				writer.getProfile().getSpecializationList().get(0).toString() : null)
			.totalIdeas(totalIdeas)
			.totalCollaborations(totalCollaborations)
			.build();
	}
}
