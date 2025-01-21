package com.brainpix.joining.entity.quantity;

import com.brainpix.post.entity.collaboration_hub.CollaborationRecruitment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Gathering extends BaseQuantity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "collaboration_recruitment_id", nullable = false)
	private CollaborationRecruitment collaborationRecruitment;

}
