package com.brainpix.post.converter;

import java.util.List;

import org.springframework.data.domain.Page;

import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;

public class GetIdeaListDtoConverter {

	public static GetIdeaListDto.Response toResponse(Page<Object[]> ideaMarkets) {

		List<GetIdeaListDto.ResponseData> responseDataList = ideaMarkets.stream()
			.map(ideaMarket ->
				toResponseData((IdeaMarket) ideaMarket[0], (Long) ideaMarket[1])
			).toList();

		return GetIdeaListDto.Response.builder()
			.responseDataList(responseDataList)
			.totalPages(ideaMarkets.getTotalPages())
			.totalElements((int)ideaMarkets.getTotalElements())
			.currentPage(ideaMarkets.getNumber())
			.currentSize(ideaMarkets.getNumberOfElements())
			.hasNext(ideaMarkets.hasNext())
			.build();
	}

	public static GetIdeaListDto.ResponseData toResponseData(IdeaMarket ideaMarket, Long saveCount) {
		return GetIdeaListDto.ResponseData.builder()
			.ideaId(ideaMarket.getId())
			.auth(ideaMarket.getPostAuth())
			.writerImageUrl(ideaMarket.getWriter().getProfileImage())
			.writerName(ideaMarket.getWriter().getName())
			.thumbnailImageUrl(ideaMarket.getImageList().get(0))
			.title(ideaMarket.getTitle())
			.price(ideaMarket.getPrice().getPrice())
			.specialization(ideaMarket.getSpecialization())
			.saveCount(saveCount)
			.viewCount(ideaMarket.getViewCount())
			.build();
	}
}
