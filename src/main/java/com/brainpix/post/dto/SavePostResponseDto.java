package com.brainpix.post.dto;

public record SavePostResponseDto(
	Boolean isSaved
) {
	public static SavePostResponseDto from(Boolean isSaved) {
		return new SavePostResponseDto(isSaved);
	}
}
