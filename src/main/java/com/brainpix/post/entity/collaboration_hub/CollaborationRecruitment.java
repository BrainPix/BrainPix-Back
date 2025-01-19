package com.brainpix.post.entity.collaboration_hub;

import com.brainpix.joining.entity.quantity.Gathering;
import com.brainpix.jpa.BaseTimeEntity;
import com.brainpix.post.dto.CollaborationRecruitmentDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	private CollaborationHub parentCollaborationHub;

	private String domain;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Gathering gathering;

	@Builder
	public CollaborationRecruitment(CollaborationHub parentCollaborationHub, String domain, Gathering gathering) {
		this.parentCollaborationHub = parentCollaborationHub;
		this.domain = domain;
		this.gathering = gathering;
	}

	public void updateRecruitmentFields(CollaborationRecruitmentDto recruitmentDto) {
		this.domain = recruitmentDto.getDomain();
		this.gathering.updateGatheringFields(recruitmentDto.getOccupiedQuantity(), recruitmentDto.getTotalQuantity());
	}
}
