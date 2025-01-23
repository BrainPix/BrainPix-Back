package com.brainpix.post.entity.collaboration_hub;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CollaborationHubProjectMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;

	private String domain;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collaboration_hub_id")
	private CollaborationHub collaborationHub;

	@Builder
	public CollaborationHubProjectMember(String userId, String domain, CollaborationHub collaborationHub) {
		this.userId = userId;
		this.domain = domain;
		this.collaborationHub = collaborationHub;
	}
}
