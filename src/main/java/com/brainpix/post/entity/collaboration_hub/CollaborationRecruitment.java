package com.brainpix.post.entity.collaboration_hub;

import java.util.ArrayList;
import java.util.List;

import com.brainpix.joining.entity.purchasing.CollectionGathering;
import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CollaborationRecruitment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "collaboration_hub_id")
	private CollaborationHub parentCollaborationHub;

	private String domain;

	@OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
	private Gathering gathering;

	@OneToMany(mappedBy = "collaborationRecruitment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CollectionGathering> collectionGatherings = new ArrayList<>();

	@Builder
	public CollaborationRecruitment(CollaborationHub parentCollaborationHub, String domain, Gathering gathering) {
		this.parentCollaborationHub = parentCollaborationHub;
		this.domain = domain;
		this.gathering = gathering;
	}
}
