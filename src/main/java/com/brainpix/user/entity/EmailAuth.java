package com.brainpix.user.entity;

import com.brainpix.jpa.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class EmailAuth extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String authCode;

	@Builder
	public EmailAuth(String email, String authCode) {
		this.email = email;
		this.authCode = authCode;
	}
}
