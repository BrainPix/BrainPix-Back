package com.brainpix.post.entity.request_task;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RequestTask extends Post {
	private LocalDateTime deadline;

	@Enumerated(EnumType.STRING)
	private RequestTaskType requestTaskType;

	@Builder
	public RequestTask(User writer, String title, String content, Boolean openMyProfile,
		Long viewCount, List<String> imageList, List<String> attachmentFileList, LocalDateTime deadline,
		RequestTaskType requestTaskType, PostAuth postAuth, Specialization specialization) {
		super(writer, title, content, openMyProfile, viewCount, postAuth, specialization, imageList,
			attachmentFileList);
		this.deadline = deadline;
		this.requestTaskType = requestTaskType;
	}
}
