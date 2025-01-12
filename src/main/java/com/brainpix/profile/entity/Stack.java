package com.brainpix.profile.entity;

import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Stack extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String stackName;

	@Enumerated(EnumType.STRING)
	private StackProficiency stackProficiency;

	@ManyToOne
	private IndividualProfile individualProfile;

	@Builder
	public Stack(String stackName, StackProficiency stackProficiency, IndividualProfile individualProfile) {
		this.stackName = stackName;
		this.stackProficiency = stackProficiency;
		this.individualProfile = individualProfile;
	}
}
