package com.brainpix.post.dto.ideamarket;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.joining.dto.IdeaMarketBuyerResponse;
import com.brainpix.post.entity.idea_market.IdeaMarketType;
import com.brainpix.profile.entity.Specialization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdeaMarketDetailResponse {
	private Long id;
	private String title;
	private String content;
	private String imageUrl;
	private LocalDateTime createdAt;
	private Specialization specialization;
	private IdeaMarketType ideaMarketType;
	private Long price;
	private List<IdeaMarketBuyerResponse> buyers;
}