package com.brainpix.post.dto;

import com.brainpix.joining.dto.IdeaMarketPriceDto;
import com.brainpix.post.entity.idea_market.IdeaMarketType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaMarketCreateDto extends PostDto {

	@NotNull(message = "아이디어 유형 선택은 필수입니다.")
	private IdeaMarketType ideaMarketType;

	@NotNull(message = "가격 및 수량 설정은 필수입니다.")
	private IdeaMarketPriceDto priceDto;
}
