package com.brainpix.post.dto;

import java.util.List;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.profile.entity.Specialization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	@NotBlank(message = "제목은 비워둘 수 없습니다.")
	private String title;

	@NotBlank(message = "내용은 비워둘 수 없습니다.")
	private String content;

	@NotBlank(message = "글 카테고리는 비워둘 수 없습니다.")
	private Specialization specialization;

	@NotNull(message = "프로필 공개 여부는 필수입니다.")
	private Boolean openMyProfile;

	@NotEmpty(message = "이미지 리스트는 비워둘 수 없습니다.")
	private List<String> imageList;

	@NotEmpty(message = "첨부파일 리스트는 비워둘 수 없습니다.")
	private List<String> attachmentFileList;

	@NotNull(message = "게시글 권한은 필수입니다.")
	private PostAuth postAuth;
}
