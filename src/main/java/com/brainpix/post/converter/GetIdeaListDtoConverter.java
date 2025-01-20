package com.brainpix.post.converter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;

public class GetIdeaListDtoConverter {

	public static GetIdeaListDto.Parameter toParameter(GetIdeaListDto.Request request, Pageable pageable) {
		return GetIdeaListDto.Parameter.builder()
			.type(request.getType())
			.keyword(request.getKeyword())
			.category(request.getCategory())
			.onlyCompany(request.getOnlyCompany())
			.sortType(request.getSortType())
			.pageable(pageable)
			.build();
	}

	public static GetIdeaListDto.Response toResponse(Page<Object[]> ideaMarkets) {

		List<GetIdeaListDto.IdeaDetail> IdeaDetailList = ideaMarkets.stream()
			.map(ideaMarket ->
				toIdeaDetail((IdeaMarket) ideaMarket[0], (Long) ideaMarket[1])
			).toList();

		return GetIdeaListDto.Response.builder()
			.ideaDetailList(IdeaDetailList)
			.totalPages(ideaMarkets.getTotalPages())
			.totalElements((int)ideaMarkets.getTotalElements())
			.currentPage(ideaMarkets.getNumber())
			.currentSize(ideaMarkets.getNumberOfElements())
			.hasNext(ideaMarkets.hasNext())
			.build();
	}

	public static GetIdeaListDto.IdeaDetail toIdeaDetail(IdeaMarket ideaMarket, Long saveCount) {
		return GetIdeaListDto.IdeaDetail.builder()
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
