package com.brainpix.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.IdeaMarketAuth;
import com.brainpix.post.entity.request_task.CollaborationType;

import lombok.Data;

@Data
public class RequestTaskUpdateDto {
	private String title;
	private String content;
	private String category;
	private Boolean openMyProfile;
	private List<String> imageList;
	private List<String> attachmentFileList;
	private LocalDateTime deadline;
	private CollaborationType collaborationType;
	private IdeaMarketAuth ideaMarketAuth;
	private List<RequestTaskRecruitmentDto> recruitments;
}
