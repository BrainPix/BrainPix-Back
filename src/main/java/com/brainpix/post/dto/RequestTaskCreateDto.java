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
public class RequestTaskCreateDto {
	private String title;
	private String content;
	private Specialization specialization;
	private Boolean openMyProfile;
	private List<String> imageList;
	private List<String> attachmentFileList;
	private LocalDateTime deadline;
	private RequestTaskType requestTaskType;
	private PostAuth postAuth;
	//@NotEmpty(message = "모집 분야는 최소 하나 이상 등록해야 합니다.")
	private List<RequestTaskRecruitmentDto> recruitments;
}
