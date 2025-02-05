package com.brainpix.user.entity;

import java.time.LocalDateTime;

import com.brainpix.api.code.error.AuthorityErrorCode;
import com.brainpix.api.exception.BrainPixException;
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

	private final static int authExpireMinute = 10;

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

	public void checkAuthTime() {
		if (this.getCreatedAt().plusMinutes(authExpireMinute).isBefore(LocalDateTime.now())) {
			throw new BrainPixException(AuthorityErrorCode.EMAIL_AUTH_CODE_EXPIRED);
		}
	}
}
