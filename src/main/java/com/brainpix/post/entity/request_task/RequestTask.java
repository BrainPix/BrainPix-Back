package com.brainpix.post.entity.request_task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.brainpix.joining.entity.quantity.Price;
import com.brainpix.post.dto.RequestTaskRecruitmentDto;
import com.brainpix.post.dto.RequestTaskUpdateDto;
import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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


	@OneToMany(mappedBy = "requestTask", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RequestTaskRecruitment> recruitments = new ArrayList<>();

	@Builder
	public RequestTask(User writer, String title, String content, String category, Boolean openMyProfile,
		Long viewCount, List<String> imageList, List<String> attachmentFileList, LocalDateTime deadline,
		RequestTaskType requestTaskType, PostAuth postAuth) {
		super(writer, title, content, category, openMyProfile, viewCount, postAuth, imageList,
			attachmentFileList);
		this.deadline = deadline;
		this.requestTaskType = requestTaskType;
	}

	public void updateRequestTaskFields(RequestTaskUpdateDto updateDto) {
		// BasePost의 필드를 업데이트
		updateBaseFields(updateDto.getTitle(), updateDto.getContent(), updateDto.getCategory(),
			updateDto.getOpenMyProfile(),
			updateDto.getPostAuth(), updateDto.getImageList(), updateDto.getAttachmentFileList());

		// RequestTask 고유 필드 업데이트
		this.deadline = updateDto.getDeadline();
		this.requestTaskType = updateDto.getRequestTaskType();
	}
}
