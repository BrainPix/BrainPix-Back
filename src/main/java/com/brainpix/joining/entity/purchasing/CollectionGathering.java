package com.brainpix.joining.entity.purchasing;

import com.brainpix.api.code.error.CollectionErrorCode;
import com.brainpix.api.exception.BrainPixException;
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
	@JoinColumn(name = "joiner_id")
	private User joiner;

	private Boolean accepted;

	private Boolean initialGathering;

	private Boolean openProfile;
	private String message;

	@ManyToOne
	@JoinColumn(name = "collaboration_recruitment_id")
	private CollaborationRecruitment collaborationRecruitment;

	@Builder
	public CollectionGathering(Long id, User joiner, Boolean accepted, Boolean initialGathering, Boolean openProfile,
		String message, CollaborationRecruitment collaborationRecruitment) {
		this.id = id;
		this.joiner = joiner;
		this.accepted = accepted;
		this.initialGathering = initialGathering;
		this.openProfile = openProfile;
		this.message = message;
		this.collaborationRecruitment = collaborationRecruitment;
	}

	public void validateJoiner(User user) {
		if (!this.joiner.equals(user)) {
			throw new BrainPixException(CollectionErrorCode.NOT_AUTHORIZED);
		}
	}

	public void validateRejectedStatus() {
		if (Boolean.TRUE.equals(this.accepted)) {
			throw new BrainPixException(CollectionErrorCode.INVALID_STATUS);
		}
	}
}
