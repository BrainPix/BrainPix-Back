package com.brainpix.joining.entity.purchasing;

import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CollectionGathering extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User joiner;

	private Boolean accepted;

	private Boolean initialGathering;

	@ManyToOne
	@JoinColumn
	private CollaborationRecruitment collaborationRecruitment;

	@Builder
	public CollectionGathering(User joiner, Boolean accepted, Boolean initialGathering,
		CollaborationRecruitment collaborationRecruitment) {
		this.joiner = joiner;
		this.accepted = accepted;
		this.initialGathering = initialGathering;
		this.collaborationRecruitment = collaborationRecruitment;
	}
}
