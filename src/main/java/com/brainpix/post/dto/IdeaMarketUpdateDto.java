package com.brainpix.post.dto;

import com.brainpix.post.entity.idea_market.IdeaMarketType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaMarketUpdateDto extends PostDto {

	@NotNull(message = "아이디어 유형 선택은 필수입니다.")
	private IdeaMarketType ideaMarketType;
}
