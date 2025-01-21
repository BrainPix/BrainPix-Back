package com.brainpix.post.entity.request_task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "requestTask", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<RequestTaskRecruitment> recruitments = new ArrayList<>();

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
