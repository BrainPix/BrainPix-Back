package com.brainpix.post.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brainpix.api.CommonPageResponse;
import com.brainpix.api.code.error.CommonErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.post.dto.GetPopularIdeaListDto;
import com.brainpix.post.entity.idea_market.IdeaMarket;
import com.brainpix.post.entity.idea_market.IdeaMarketType;

public class GetPopularIdeaListDtoConverter {

	public static GetPopularIdeaListDto.Parameter toParameter(Long userId, GetPopularIdeaListDto.Request request,
		Pageable pageable) {
		IdeaMarketType type = null;

		try {
			type = request.getType() != null ? IdeaMarketType.valueOf(request.getType().toUpperCase()) : null;
		} catch (Exception e) {
			throw new BrainPixException(CommonErrorCode.INVALID_PARAMETER);
		}

		return GetPopularIdeaListDto.Parameter.builder()
			.userId(userId)
			.type(type)
			.pageable(pageable)
			.build();
	}

	public static CommonPageResponse<GetPopularIdeaListDto.IdeaDetail> toResponse(Page<Object[]> ideaMarkets) {

		Page<GetPopularIdeaListDto.IdeaDetail> response = ideaMarkets.map(
			ideaMarket -> toIdeaDetail((IdeaMarket)ideaMarket[0], (Long)ideaMarket[1], (Boolean)ideaMarket[2])
		);

		return CommonPageResponse.of(response);
	}

	public static GetPopularIdeaListDto.IdeaDetail toIdeaDetail(IdeaMarket ideaMarket, Long saveCount,
		Boolean isSavedPost) {

		return GetPopularIdeaListDto.IdeaDetail.builder()
			.ideaId(ideaMarket.getId())
			.auth(ideaMarket.getPostAuth().toString())
			.writerImageUrl(ideaMarket.getWriter().getProfileImage())
			.writerName(ideaMarket.getWriter().getName())
			.thumbnailImageUrl(ideaMarket.getImageList() != null ? ideaMarket.getImageList().get(0) : null)
			.title(ideaMarket.getTitle())
			.price(ideaMarket.getPrice().getPrice())
			.category(ideaMarket.getSpecialization().toString())
			.saveCount(saveCount)
			.viewCount(ideaMarket.getViewCount())
			.isSavedPost(isSavedPost)
			.build();
	}
}
