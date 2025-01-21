package com.brainpix.post.dto;

import java.util.List;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.profile.entity.Specialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private String title;
	private String content;
	private Specialization specialization;
	private Boolean openMyProfile;
	private List<String> imageList;
	private List<String> attachmentFileList;
	private PostAuth postAuth;
}
