package com.brainpix.joining.entity.purchasing;

import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;
import com.brainpix.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CollectionGathering {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User joiner;

	private Boolean accepted;

	@ManyToOne
	private CollaborationRecruitment collaborationRecruitment;

	public CollectionGathering(User joiner, Boolean accepted, CollaborationRecruitment collaborationRecruitment) {
		this.joiner = joiner;
		this.accepted = accepted;
		this.collaborationRecruitment = collaborationRecruitment;
	}
}
