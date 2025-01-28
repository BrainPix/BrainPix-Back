package com.brainpix.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationSupportInfo {
	private String userId;
	private String role;
	private String currentSlashTotal;
}
