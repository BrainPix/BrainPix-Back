package com.brainpix.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.PostAuth;
import com.brainpix.post.entity.request_task.RequestTaskType;
import com.brainpix.profile.entity.Specialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTaskUpdateDto {
	private String title;
	private String content;
	private Specialization specialization;
	private Boolean openMyProfile;
	private List<String> imageList;
	private List<String> attachmentFileList;
	private LocalDateTime deadline;
	private RequestTaskType requestTaskType;
	private PostAuth postAuth;
}
