package com.brainpix.post.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.GetIdeaListDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.post.enums.SortType;
import com.brainpix.profile.entity.Specialization;

public class GetIdeaListDtoConverter {

	public static GetIdeaListDto.Parameter toParameter(Long userId, GetIdeaListDto.Request request, Pageable pageable) {

		IdeaMarketType type = null;
		Specialization category = null;
		SortType sortType = null;

		try {
			type = request.getType() != null ? IdeaMarketType.valueOf(request.getType().toUpperCase()) : null;
			category =
				request.getCategory() != null ? Specialization.valueOf(request.getCategory().toUpperCase()) : null;
			sortType =
				request.getSortType() != null ? SortType.valueOf("IDEA_" + request.getSortType().toUpperCase()) : null;
		} catch (Exception e) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		return GetIdeaListDto.Parameter.builder()
			.userId(userId)
			.type(type)
			.keyword(request.getKeyword())
			.category(category)
			.onlyCompany(request.getOnlyCompany())
			.sortType(sortType)
			.pageable(pageable)
			.build();
	}

	public static CommonPageResponse<GetIdeaListDto.IdeaDetail> toResponse(Page<Object[]> ideaMarkets) {

		Page<GetIdeaListDto.IdeaDetail> response = ideaMarkets.map(
			ideaMarket -> toIdeaDetail((IdeaMarket)ideaMarket[0], (Long)ideaMarket[1], (Boolean)ideaMarket[2])
		);

		return CommonPageResponse.of(response);
	}

	public static GetIdeaListDto.IdeaDetail toIdeaDetail(IdeaMarket ideaMarket, Long saveCount, Boolean isSavedPost) {
		return GetIdeaListDto.IdeaDetail.builder()
			.ideaId(ideaMarket.getId())
			.auth(ideaMarket.getPostAuth().toString())
			.writerImageUrl(ideaMarket.getWriter().getProfileImage())
			.writerName(ideaMarket.getWriter().getNickName())
			.thumbnailImageUrl(!ideaMarket.getImageList().isEmpty() ? ideaMarket.getImageList().get(0) : null)
			.title(ideaMarket.getTitle())
			.price(ideaMarket.getPrice().getPrice())
			.category(ideaMarket.getSpecialization().toString())
			.saveCount(saveCount)
			.viewCount(ideaMarket.getViewCount())
			.isSavedPost(isSavedPost)
			.build();
	}
}
