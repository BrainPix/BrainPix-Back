package com.brainpix.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.PostAuth;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationHubUpdateDto extends PostDto{

	@Future(message = "마감일은 현재 날짜보다 미래여야 합니다.")
	@NotNull(message = "마감일은 필수입니다.")
	private LocalDateTime deadline;

	private String link;

}
