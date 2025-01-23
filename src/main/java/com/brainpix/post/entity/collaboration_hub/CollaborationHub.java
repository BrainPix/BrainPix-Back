package com.brainpix.post.entity.collaboration_hub;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.post.dto.CollaborationHubUpdateDto;
import com.brainpix.post.dto.CollaborationRecruitmentDto;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.post.entity.Post;
import com.brainpix.post.entity.PostAuth;
import com.brainpix.profile.entity.Specialization;
import com.brainpix.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CollaborationHub extends Post {
	private LocalDateTime deadline;
	private String link;

	@OneToMany(mappedBy = "parentCollaborationHub", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CollaborationRecruitment> collaborations = new ArrayList<>();

	@Builder
	public CollaborationHub(User writer, String title, String content, Boolean openMyProfile,
		Long viewCount, List<String> imageList, List<String> attachmentFileList, LocalDateTime deadline,
		String link, PostAuth postAuth, Specialization specialization) {
		super(writer, title, content, openMyProfile, viewCount, postAuth, specialization, imageList,
			attachmentFileList);
		this.deadline = deadline;
		this.link = link;
	}

	public void updateCollaborationHubFields(CollaborationHubUpdateDto updateDto, List<CollaborationRecruitmentDto> recruitmentDtos) {
		// BasePost의 필드를 업데이트
		updateBaseFields(updateDto.getTitle(), updateDto.getContent(), updateDto.getCategory(),
			updateDto.getOpenMyProfile(),
			updateDto.getPostAuth(), updateDto.getImageList(), updateDto.getAttachmentFileList());

		// RequestTask 고유 필드 업데이트
		this.deadline = updateDto.getDeadline();
		this.link = updateDto.getLink();

		// recruitments 업데이트
		if (recruitmentDtos != null) {
			for (int i = 0; i < recruitmentDtos.size(); i++) {
				if (i < this.collaborations.size()) {
					// 기존 Recruitment 업데이트
					this.collaborations.get(i).updateRecruitmentFields(recruitmentDtos.get(i));
				} else {
					// 새 Recruitment 추가
					CollaborationRecruitmentDto recruitmentDto = recruitmentDtos.get(i);
					this.collaborations.add(new CollaborationRecruitment(
						this,
						recruitmentDto.getDomain(),
						new Gathering(recruitmentDto.getOccupiedQuantity(), recruitmentDto.getTotalQuantity())
					));
				}
			}
			// 남아있는 recruitments가 더 많을 경우 삭제
			if (recruitmentDtos.size() < this.collaborations.size()) {
				this.collaborations.subList(recruitmentDtos.size(), this.collaborations.size()).clear();
			}
		}

	}

	public long getTotalQuantity() {
		return this.getCollaborations()
			.stream()
			.map(CollaborationRecruitment::getGathering)
			.filter(Objects::nonNull)
			.mapToLong(Gathering::getTotalQuantity)
			.sum();
	}

	// 모집 현재 인원 합산 메서드
	public long getOccupiedQuantity() {
		return this.getCollaborations()
			.stream()
			.map(CollaborationRecruitment::getGathering)
			.filter(Objects::nonNull)
			.mapToLong(Gathering::getOccupiedQuantity)
			.sum();
	}
}
