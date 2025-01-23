package com.brainpix.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CreatePortfolioDto {

	@Getter
	@AllArgsConstructor
	public static class Response {
		private Long portfolioId;
	}
}
