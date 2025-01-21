package com.brainpix.post.converter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public class GetPopularIdeaListDtoConverter {

	public static GetPopularIdeaListDto.Parameter toParameter(GetPopularIdeaListDto.Request request, Pageable pageable) {
		IdeaMarketType type = null;

		try {
			type = request.getType() != null ? IdeaMarketType.valueOf(request.getType().toUpperCase()) : null;
		} catch (Exception e) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		return GetPopularIdeaListDto.Parameter.builder()
			.type(type)
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
			.auth(ideaMarket.getPostAuth().toString())
			.writerImageUrl(ideaMarket.getWriter().getProfileImage())
			.writerName(ideaMarket.getWriter().getName())
			.thumbnailImageUrl(ideaMarket.getImageList().get(0))
			.title(ideaMarket.getTitle())
			.price(ideaMarket.getPrice().getPrice())
			.category(ideaMarket.getSpecialization().toString())
			.saveCount(saveCount)
			.viewCount(ideaMarket.getViewCount())
			.build();
	}
}
