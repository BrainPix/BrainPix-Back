package com.brainpix.post.entity.collaboration_hub;

import com.brainpix.post.entity.request_task.CollaborationType;
import com.brainpix.post.entity.request_task.RequestTaskRecruitment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.brainpix.post.entity.BasePost;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CollaborationHub extends BasePost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime deadline;

	private String link;

	@Enumerated(EnumType.STRING)
	private CollaborationType collaborationType;

	@OneToMany(mappedBy = "parentCollaborationHub", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CollaborationRecruitment> recruitments = new ArrayList<>();

	@Builder
	public CollaborationHub(User writer, String title, String contest, String category, Boolean openMyProfile,
		Long viewCount, List<String> imageList, List<String> attachmentFileList, LocalDateTime deadline,String link,
		CollaborationType collaborationType) {
		super(writer, title, contest, category, openMyProfile, viewCount, imageList, attachmentFileList);
		this.deadline = deadline;
		this.collaborationType = collaborationType;
		this.link = link;
	}
}
