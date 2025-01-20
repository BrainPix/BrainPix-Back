package com.brainpix.post.converter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;

public class GetPopularIdeaListDtoConverter {

	public static GetPopularIdeaListDto.Parameter toParameter(GetPopularIdeaListDto.Request request, Pageable pageable) {
		return GetPopularIdeaListDto.Parameter.builder()
			.type(request.getType())
			.pageable(pageable)
			.build();
	}

	public static GetPopularIdeaListDto.Response toResponse(Page<Object[]> ideaMarkets) {

		List<GetPopularIdeaListDto.IdeaDetail> ideaDetailList = ideaMarkets.stream()
			.map(ideaMarket ->
				toIdeaDetail((IdeaMarket) ideaMarket[0], (Long) ideaMarket[1])
			).toList();

		return GetPopularIdeaListDto.Response.builder()
			.ideaDetailList(ideaDetailList)
			.totalPages(ideaMarkets.getTotalPages())
			.totalElements((int)ideaMarkets.getTotalElements())
			.currentPage(ideaMarkets.getNumber())
			.currentSize(ideaMarkets.getNumberOfElements())
			.hasNext(ideaMarkets.hasNext())
			.build();
	}

	public static GetPopularIdeaListDto.IdeaDetail toIdeaDetail(IdeaMarket ideaMarket, Long saveCount) {

		return GetPopularIdeaListDto.IdeaDetail.builder()
			.ideaId(ideaMarket.getId())
			.auth(ideaMarket.getPostAuth())
			.writerImageUrl(ideaMarket.getWriter().getProfileImage())
			.writerName(ideaMarket.getWriter().getName())
			.thumbnailImageUrl(ideaMarket.getImageList().get(0))
			.title(ideaMarket.getTitle())
			.price(ideaMarket.getPrice().getPrice())
			.category(ideaMarket.getSpecialization())
			.saveCount(saveCount)
			.viewCount(ideaMarket.getViewCount())
			.build();
	}
}
