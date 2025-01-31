package com.brainpix.post.converter;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public class GetIdeaListDtoConverter {

	public static GetIdeaListDto.Parameter toParameter(GetIdeaListDto.Request request, Pageable pageable) {

		IdeaMarketType type = null;
		Specialization category = null;
		SortType sortType = null;

		try {
			type = request.getType() != null ? IdeaMarketType.valueOf(request.getType().toUpperCase()) : null;
			category = request.getCategory() != null ? Specialization.valueOf(request.getCategory().toUpperCase()) : null;
			sortType = request.getSortType() != null ? SortType.valueOf("IDEA_" + request.getSortType().toUpperCase()) : null;
		} catch (Exception e) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		return GetIdeaListDto.Parameter.builder()
			.type(type)
			.keyword(request.getKeyword())
			.category(category)
			.onlyCompany(request.getOnlyCompany())
			.sortType(sortType)
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
