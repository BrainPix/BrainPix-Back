package com.brainpix.post.entity.request_task;

import java.time.LocalDateTime;
import java.util.List;

import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.IdeaMarketAuth;
import com.brainpix.post.entity.collaboration_hub.CollaborationType;
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
	private CollaborationType collaborationType;

	/*
	@OneToMany(mappedBy = "requestTask", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RequestTaskRecruitment> recruitments = new ArrayList<>();
	 */

	@Builder
	public RequestTask(User writer, String title, String content, String category, Boolean openMyProfile,
		Long viewCount, List<String> imageList, List<String> attachmentFileList, LocalDateTime deadline,
		CollaborationType collaborationType, IdeaMarketAuth ideaMarketAuth) {
		super(writer, title, content, category, openMyProfile, viewCount, ideaMarketAuth, imageList,
			attachmentFileList);
		this.deadline = deadline;
		this.collaborationType = collaborationType;
	}

	/*
	public void updateRequestTaskFields(RequestTaskUpdateDto updateDto) {
		// BasePost의 필드를 업데이트
		updateBaseFields(updateDto.getTitle(), updateDto.getContent(), updateDto.getCategory(), updateDto.getOpenMyProfile(),
			updateDto.getIdeaMarketAuth() ,updateDto.getImageList(), updateDto.getAttachmentFileList());

		// RequestTask 고유 필드 업데이트
		this.deadline = updateDto.getDeadline();
		this.collaborationType = updateDto.getCollaborationType();
	}

	public void updateRecruitmentFields(List<RequestTaskRecruitmentDto> recruitmentDtos) {
		this.recruitments.clear(); // 기존 데이터 제거
		for (RequestTaskRecruitmentDto recruitmentDto : recruitmentDtos) {
			this.recruitments.add(
				new RequestTaskRecruitment(this, recruitmentDto.getDomain(), recruitmentDto.getPrice(), recruitmentDto.getCurrentPeople(), recruitmentDto.getTotalPeople() ,recruitmentDto.getAgreementType()));
		}
	}
	*/

}
